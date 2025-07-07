package com.sise.ahorroapp.backend.controller;

import com.sise.ahorroapp.backend.entidad.Movimiento;
import com.sise.ahorroapp.backend.entidad.Usuario;
import com.sise.ahorroapp.backend.seguridad.UsuarioDetails;
import com.sise.ahorroapp.backend.servicio.MovimientoServicio;
import com.sise.ahorroapp.backend.servicio.UsuarioServicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class WebUsuarioController {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private MovimientoServicio movimientoServicio;

    // 🌟 DASHBOARD del usuario logueado (últimos 5 movimientos)
    @GetMapping("/usuario")
    public String vistaUsuario(Model model, Principal principal) {
        Usuario usuario = usuarioServicio.buscarPorCorreo(principal.getName());

        double ingresos = movimientoServicio.obtenerTotalPorTipoYUsuario("INGRESO", usuario);
        double gastos = movimientoServicio.obtenerTotalPorTipoYUsuario("GASTO", usuario);
        double balance = ingresos - gastos;

        List<Movimiento> movimientos = movimientoServicio.ultimosMovimientosPorUsuario(usuario.getId());

        model.addAttribute("usuario", usuario);
        model.addAttribute("ingresos", ingresos);
        model.addAttribute("gastos", gastos);
        model.addAttribute("balance", balance);
        model.addAttribute("metaAhorro", usuario.getMetaAhorro());
        model.addAttribute("ahorroActual", balance);
        model.addAttribute("ultimosMovimientos", movimientos);

        return "usuario_dashboard";
    }

    // 🌟 PAGINACIÓN de todos los movimientos del usuario
    @GetMapping("/usuario/movimientos")
    public String listarMovimientosUsuario(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "5") int size,
                                           Model model,
                                           Principal principal) {
        Usuario usuario = usuarioServicio.buscarPorCorreo(principal.getName());

        Pageable pageable = PageRequest.of(page, size);
        Page<Movimiento> movimientosPage = movimientoServicio.listarMovimientosPorUsuario(usuario.getId(), pageable);

        model.addAttribute("usuario", usuario);
        model.addAttribute("movimientosPage", movimientosPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", movimientosPage.getTotalPages());

        return "movimientos_usuario"; // Vista que debe mostrar la tabla paginada
    }

    // 🌟 PERFIL DEL USUARIO
    @GetMapping("/perfil")
    public String verPerfil(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof UsuarioDetails userDetails) {
            model.addAttribute("usuario", userDetails.getUsuario());
        }
        return "perfil";
    }

    // 🌟 ACTUALIZAR USUARIO
    @PostMapping("/usuarios/actualizar")
    public String actualizarUsuario(@ModelAttribute("usuario") Usuario usuario) {
        usuarioServicio.guardarUsuario(usuario);
        return "redirect:/usuarios?actualizado=true";
    }

    // 🌟 FORMULARIO DE REGISTRO
    @GetMapping("/usuarios/nuevo")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "nuevo_usuario";
    }

    // 🌟 GUARDAR USUARIO
    @PostMapping("/usuarios/guardar")
    public String guardarUsuario(@ModelAttribute("usuario") Usuario usuario) {
        usuario.setRol("USER");
        usuario.setActivo(true);
        usuarioServicio.guardarUsuario(usuario);
        return "redirect:/?creado=true";
    }

    // 🌟 LISTADO DE USUARIOS
    @GetMapping("/usuarios")
    public String mostrarUsuarios(Model model) {
        List<Usuario> usuarios = usuarioServicio.listarUsuarios();
        model.addAttribute("usuarios", usuarios);
        return "usuarios";
    }

    // 🌟 EDITAR USUARIO
    @GetMapping("/usuarios/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") Long id, Model model) {
        Usuario usuario = usuarioServicio.obtenerUsuarioPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        model.addAttribute("usuario", usuario);
        return "nuevo_usuario";
    }

    // 🌟 DESACTIVAR USUARIO
    @GetMapping("/usuarios/desactivar/{id}")
    public String desactivarUsuario(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        usuarioServicio.obtenerUsuarioPorId(id).ifPresent(usuario -> {
            usuario.setActivo(false);
            usuarioServicio.guardarUsuario(usuario);
            redirectAttributes.addFlashAttribute("mensajeExito", "Usuario desactivado correctamente.");
        });
        return "redirect:/usuarios";
    }

    // 🌟 ACTIVAR USUARIO
    @GetMapping("/usuarios/activar/{id}")
    public String activarUsuario(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        usuarioServicio.obtenerUsuarioPorId(id).ifPresent(usuario -> {
            usuario.setActivo(true);
            usuarioServicio.guardarUsuario(usuario);
            redirectAttributes.addFlashAttribute("mensajeExito", "Usuario activado correctamente.");
        });
        return "redirect:/usuarios";
    }
}
