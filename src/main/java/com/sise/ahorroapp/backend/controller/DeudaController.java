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
        model.addAttribute("deudas", deudaServicio.listarPorUsuario(usuario.getId()));
        return "deudas"; // Vista de listado de deudas
    }

    // ✅ Mostrar formulario nueva deuda
    @GetMapping("/nueva")
    public String nuevaDeuda(Model model) {
        model.addAttribute("deuda", new Deuda());
        return "form_deuda"; // Asegúrate que este HTML exista
    }

    // ✅ Guardar o actualizar deuda
    @PostMapping("/guardar")
    public String guardarDeuda(@ModelAttribute Deuda deuda, Principal principal) {
        Usuario usuario = usuarioServicio.buscarPorCorreo(principal.getName());
        deuda.setUsuario(usuario);
        deudaServicio.guardar(deuda); // ✅ Método actualizado
        return "redirect:/usuario/deudas";
    }

    // ✅ Editar deuda
    @GetMapping("/editar/{id}")
    public String editarDeuda(@PathVariable Long id, Model model) {
        Deuda deuda = deudaServicio.buscarPorId(id);
        if (deuda == null) {
            return "redirect:/usuario/deudas?error=notfound";
        }
        model.addAttribute("deuda", deuda);
        return "form_deuda";
    }

    // ✅ Eliminar deuda
    @GetMapping("/eliminar/{id}")
    public String eliminarDeuda(@PathVariable Long id) {
        deudaServicio.eliminar(id);
        return "redirect:/usuario/deudas";
    }
}

