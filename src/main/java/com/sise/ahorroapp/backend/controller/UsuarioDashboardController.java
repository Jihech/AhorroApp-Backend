package com.sise.ahorroapp.backend.controller;

import com.sise.ahorroapp.backend.entidad.Movimiento;
import com.sise.ahorroapp.backend.entidad.Usuario;
import com.sise.ahorroapp.backend.entidad.Deuda;
import com.sise.ahorroapp.backend.servicio.MovimientoServicio;
import com.sise.ahorroapp.backend.servicio.UsuarioServicio;
import com.sise.ahorroapp.backend.servicio.DeudaServicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class UsuarioDashboardController {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private MovimientoServicio movimientoServicio;

    @Autowired
    private DeudaServicio deudaServicio;

    @GetMapping("/usuario/dashboard")
    public String mostrarDashboardUsuario(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/?error=sesion";
        }

        String correo = principal.getName();
        Usuario usuario = usuarioServicio.buscarPorCorreo(correo);

        if (usuario == null) {
            return "redirect:/?error=usuario-no-encontrado";
        }

        double ingresos = movimientoServicio.obtenerTotalPorTipoYUsuario("INGRESO", usuario);
        double gastos = movimientoServicio.obtenerTotalPorTipoYUsuario("GASTO", usuario);
        double balance = ingresos - gastos;

        List<Movimiento> movimientos = movimientoServicio.ultimosMovimientosPorUsuario(usuario.getId());


        // ✅ Corrección: pasar el ID del usuario
        List<Deuda> deudasPendientes = deudaServicio
                .listarPorUsuario(usuario.getId())
                .stream()
                .filter(deuda -> !deuda.getPagada())
                .toList();

        model.addAttribute("usuario", usuario);
        model.addAttribute("ingresos", ingresos);
        model.addAttribute("gastos", gastos);
        model.addAttribute("balance", balance);
        model.addAttribute("metaAhorro", usuario.getMetaAhorro() != null ? usuario.getMetaAhorro() : 0);
        model.addAttribute("ahorroActual", balance);
        model.addAttribute("ultimosMovimientos", movimientos);
        model.addAttribute("deudasPendientes", deudasPendientes);

        return "usuario-dashboard";
    }

    @PostMapping("/usuario/actualizar-meta")
    public String actualizarMeta(@RequestParam("nuevaMeta") double nuevaMeta,
                                 Principal principal,
                                 RedirectAttributes redirectAttrs) {
        if (nuevaMeta <= 0) {
            redirectAttrs.addFlashAttribute("mensajeError", "❌ La meta debe ser mayor que cero.");
            return "redirect:/usuario/dashboard";
        }

        Usuario usuario = usuarioServicio.buscarPorCorreo(principal.getName());
        usuario.setMetaAhorro(nuevaMeta);
        usuarioServicio.guardarUsuario(usuario);

        redirectAttrs.addFlashAttribute("mensaje", "✅ Meta de ahorro actualizada con éxito.");
        return "redirect:/usuario/dashboard";
    }
}

