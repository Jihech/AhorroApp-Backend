package com.sise.ahorroapp.backend.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sise.ahorroapp.backend.entidad.Deuda;
import com.sise.ahorroapp.backend.entidad.Usuario;
import com.sise.ahorroapp.backend.servicio.DeudaServicio;
import com.sise.ahorroapp.backend.servicio.UsuarioServicio;

@Controller
@RequestMapping("/usuario/deudas")
public class DeudaController {

    @Autowired
    private DeudaServicio deudaServicio;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping
    public String listarDeudas(Model model, Principal principal) {
        Usuario usuario = usuarioServicio.buscarPorCorreo(principal.getName());
        model.addAttribute("deudas", deudaServicio.listarPorUsuario(usuario.getId()));
        return "deudas"; // HTML listado
    }

    @GetMapping("/nueva")
    public String nuevaDeuda(Model model) {
        model.addAttribute("deuda", new Deuda());
        return "form_deudas";
    }

    @PostMapping("/guardar")
    public String guardarDeuda(@ModelAttribute Deuda deuda, Principal principal) {
        Usuario usuario = usuarioServicio.buscarPorCorreo(principal.getName());
        deuda.setUsuario(usuario);
        deudaServicio.guardarDeuda(deuda);
        return "redirect:/usuario/deudas";
    }

    @GetMapping("/editar/{id}")
    public String editarDeuda(@PathVariable Long id, Model model) {
        Deuda deuda = deudaServicio.obtenerPorId(id)
            .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        model.addAttribute("deuda", deuda);
        return "form_deudas";
    }
    
    
}
