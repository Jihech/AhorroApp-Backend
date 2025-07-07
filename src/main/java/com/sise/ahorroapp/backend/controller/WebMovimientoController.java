package com.sise.ahorroapp.backend.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.sise.ahorroapp.backend.entidad.Movimiento;
import com.sise.ahorroapp.backend.servicio.MovimientoServicio;
import com.sise.ahorroapp.backend.servicio.UsuarioServicio;

@Controller
public class WebMovimientoController {

    @Autowired
    private MovimientoServicio movimientoService;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/movimientos/nuevo")
    public String mostrarMovimiento(Model model) {
        model.addAttribute("movimiento", new Movimiento());
        model.addAttribute("usuarios", usuarioServicio.listarUsuarios());
        return "form_movimiento";
    }

    @PostMapping("/movimientos/guardar")
    public String guardarMovimiento(@ModelAttribute Movimiento movimiento) {
    	movimiento.setFecha(LocalDate.now());
        movimientoService.guardarMovimiento(movimiento);
        return "redirect:/usuario/dashboard"; // o /movimientos si haces lista
    }
    
    @GetMapping("/movimientos")
    public String verMovimientos(Model model) {
        List<Movimiento> movimientos = movimientoService.listarMovimientos();
        model.addAttribute("movimientos", movimientos);
        return "movimientos";
    }

}
