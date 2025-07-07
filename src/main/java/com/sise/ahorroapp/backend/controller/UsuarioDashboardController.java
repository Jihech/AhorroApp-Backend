package com.sise.ahorroapp.backend.controller;

import com.sise.ahorroapp.backend.entidad.Movimiento;
import com.sise.ahorroapp.backend.entidad.Usuario;
import com.sise.ahorroapp.backend.servicio.MovimientoServicio;
import com.sise.ahorroapp.backend.servicio.UsuarioServicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class UsuarioDashboardController {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private MovimientoServicio movimientoServicio;

    @GetMapping("/usuario/dashboard")
    public String mostrarDashboardUsuario(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/?error=sesion";
        }

        // Obtener el usuario autenticado por su correo
        String correo = principal.getName();
        Usuario usuario = usuarioServicio.buscarPorCorreo(correo);

        if (usuario == null) {
            return "redirect:/?error=usuario-no-encontrado";
        }

        // Calcular ingresos, gastos y balance
        double ingresos = movimientoServicio.obtenerTotalPorTipoYUsuario("INGRESO", usuario);
        double gastos = movimientoServicio.obtenerTotalPorTipoYUsuario("GASTO", usuario);
        double balance = ingresos - gastos;

        // Obtener los últimos 5 movimientos
        List<Movimiento> movimientos = movimientoServicio.ultimosMovimientosPorUsuario(usuario.getId(), 5);

        // Agregar atributos al modelo
        model.addAttribute("usuario", usuario); // ✅ Para mostrar el nombre, etc.
        model.addAttribute("ingresos", ingresos);
        model.addAttribute("gastos", gastos);
        model.addAttribute("balance", balance);
        model.addAttribute("metaAhorro", usuario.getMetaAhorro() != null ? usuario.getMetaAhorro() : 0);
        model.addAttribute("ahorroActual", balance);
        model.addAttribute("ultimosMovimientos", movimientos); // ✅ Para la tabla

        return "usuario_dashboard"; // Debe existir usuario_dashboard.html en templates
    }
}
