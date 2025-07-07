package com.sise.ahorroapp.backend.controller;

import com.sise.ahorroapp.backend.servicio.MovimientoServicio;
import com.sise.ahorroapp.backend.servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminDashboardController {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private MovimientoServicio movimientoServicio;

    @GetMapping("/dashboard")
    public String verDashboardAdmin(Model model) {
        long totalUsuarios = usuarioServicio.contarUsuarios();
        long totalMovimientos = movimientoServicio.contarMovimientos();

        model.addAttribute("totalUsuarios", totalUsuarios);
        model.addAttribute("totalMovimientos", totalMovimientos);

        return "admin_dashboard";
    }
}
