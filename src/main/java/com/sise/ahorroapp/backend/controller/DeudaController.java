package com.sise.ahorroapp.backend.controller;

import com.sise.ahorroapp.backend.entidad.Deuda;
import com.sise.ahorroapp.backend.entidad.Usuario;
import com.sise.ahorroapp.backend.servicio.DeudaServicio;
import com.sise.ahorroapp.backend.servicio.UsuarioServicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/usuario/deudas")
public class DeudaController {

    @Autowired
    private DeudaServicio deudaServicio;

    @Autowired
    private UsuarioServicio usuarioServicio;

    // ✅ Listar deudas del usuario
    @GetMapping
    public String listarDeudas(Model model, Principal principal) {
        Usuario usuario = usuarioServicio.buscarPorCorreo(principal.getName());
        List<Deuda> deudas = deudaServicio.listarPorUsuario(usuario.getId());
        model.addAttribute("deudas", deudas);
        return "deudas";
    }

    // ✅ Mostrar formulario para nueva deuda
    @GetMapping("/nueva")
    public String mostrarFormularioNuevaDeuda(Model model) {
        model.addAttribute("deuda", new Deuda());
        return "form_deudas";
    }

    // ✅ Guardar o actualizar deuda
    @PostMapping("/guardar")
    public String guardarDeuda(@ModelAttribute Deuda deuda, Principal principal) {
        Usuario usuario = usuarioServicio.buscarPorCorreo(principal.getName());
        deuda.setUsuario(usuario);

        // Validación de nulo en pagada
        if (deuda.getPagada() == null) {
            deuda.setPagada(false);
        }

        deudaServicio.guardar(deuda);
        return "redirect:/usuario/deudas";
    }

    // ✅ Editar deuda existente
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarDeuda(@PathVariable Long id, Model model, Principal principal) {
        Deuda deuda = deudaServicio.buscarPorId(id);

        if (deuda == null || !deuda.getUsuario().getCorreo().equals(principal.getName())) {
            return "redirect:/usuario/deudas?error=acceso-denegado";
        }

        model.addAttribute("deuda", deuda);
        return "form_deudas";
    }

    // ✅ Eliminar deuda
    @GetMapping("/eliminar/{id}")
    public String eliminarDeuda(@PathVariable Long id, Principal principal) {
        Deuda deuda = deudaServicio.buscarPorId(id);

        if (deuda != null && deuda.getUsuario().getCorreo().equals(principal.getName())) {
            deudaServicio.eliminar(id);
        }

        return "redirect:/usuario/deudas";
    }
}

