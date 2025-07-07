package com.sise.ahorroapp.backend.controller;

import com.sise.ahorroapp.backend.servicio.MovimientoServicio;
import com.sise.ahorroapp.backend.servicio.UsuarioServicio;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sise.ahorroapp.backend.entidad.Movimiento;
import com.sise.ahorroapp.backend.entidad.Usuario;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.List;

@Controller
public class AdminDashboardController {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private MovimientoServicio movimientoServicio;

    @GetMapping("/admin/dashboard")
    public String verDashboardAdmin(Model model) {
        long totalUsuarios = usuarioServicio.contarUsuarios();
        long totalMovimientos = movimientoServicio.contarMovimientos();

        model.addAttribute("totalUsuarios", totalUsuarios);
        model.addAttribute("totalMovimientos", totalMovimientos);
        model.addAttribute("usuarios", usuarioServicio.listarUsuarios());
        model.addAttribute("movimientos", movimientoServicio.listarMovimientos());

        return "admin-dashboard"; // templates/admin_dashboard.html
    }
    
    @GetMapping("/admin/movimientos")
    public String verMovimientos(Model model,
                                 @RequestParam(required = false) String tipo,
                                 @RequestParam(required = false) Long usuarioId,
                                 @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
                                 @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta,
                                 @PageableDefault(size = 20, sort = "fecha", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<Movimiento> movimientos = movimientoServicio.filtrarMovimientos(tipo, usuarioId, desde, hasta, pageable);
        List<Usuario> usuarios = usuarioServicio.listarUsuarios();

        model.addAttribute("movimientos", movimientos);
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("tipo", tipo);
        model.addAttribute("usuarioId", usuarioId);
        model.addAttribute("desde", desde);
        model.addAttribute("hasta", hasta);

        return "admin/movimientos"; // Thymeleaf o fragmento AJAX
    }


}
