package com.sise.ahorroapp.backend.controller;

import java.security.Principal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sise.ahorroapp.backend.entidad.Movimiento;
import com.sise.ahorroapp.backend.entidad.Usuario;
import com.sise.ahorroapp.backend.servicio.MovimientoServicio;
import com.sise.ahorroapp.backend.servicio.UsuarioServicio;

@Controller
@RequestMapping("usuario/movimiento")
public class MovimientoUsuarioController {

    @Autowired
    private MovimientoServicio movimientoServicio;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevoMovimiento(Model model) {
        model.addAttribute("movimiento", new Movimiento());
        return "form_movimiento";
    }

    @PostMapping("/guardar")
    public String guardarMovimiento(@ModelAttribute Movimiento movimiento, Principal principal) {
        Usuario usuario = usuarioServicio.buscarPorCorreo(principal.getName());
        movimiento.setUsuario(usuario);

        // Si no se estableció fecha en el formulario, asigna la fecha actual
        if (movimiento.getFecha() == null) {
            movimiento.setFecha(LocalDate.now());
        }

        movimientoServicio.guardar(movimiento);
        return "redirect:/usuario/dashboard";
    }

}
