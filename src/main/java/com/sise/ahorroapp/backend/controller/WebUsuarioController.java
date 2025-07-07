package com.sise.ahorroapp.backend.controller;

import com.sise.ahorroapp.backend.entidad.Movimiento;
import com.sise.ahorroapp.backend.entidad.Usuario;
import com.sise.ahorroapp.backend.seguridad.UsuarioDetails;
import com.sise.ahorroapp.backend.servicio.MovimientoServicio;
import com.sise.ahorroapp.backend.servicio.UsuarioServicio;

import org.springframework.beans.factory.annotation.Autowired;
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

    // 🌟 INICIO - carga index.html con objeto usuario
    /*@GetMapping("/")
    public String verInicio(Model model, @RequestParam(value = "error", required = false) String error) {
        model.addAttribute("usuario", new Usuario()); // necesario para el registro
        if (error != null) {
            model.addAttribute("error", "Credenciales inválidas. Intenta nuevamente.");
        }
        return "index";
    }*/

    // 🌟 DASHBOARD PERSONAL DEL USUARIO LOGUEADO
    /*@GetMapping("/usuario")
    public String vistaUsuario(Model model, Principal principal) {
        Usuario usuario = usuarioServicio.buscarPorCorreo(principal.getName());
        model.addAttribute("usuario", usuario);
        return "usuario_dashboard"; // Asegúrate de tener esta vista
    }*/
    
    @GetMapping("/usuario")
    public String vistaUsuario(Model model, Principal principal) {
        // Buscar usuario por correo
        Usuario usuario = usuarioServicio.buscarPorCorreo(principal.getName());
        model.addAttribute("usuario", usuario);

        // Calcular ingresos, gastos y balance
        double ingresos = movimientoServicio.obtenerTotalPorTipoYUsuario("INGRESO", usuario);
        double gastos = movimientoServicio.obtenerTotalPorTipoYUsuario("GASTO", usuario);
        double balance = ingresos - gastos;

        // Obtener últimos movimientos (máx. 5)
        List<Movimiento> movimientos = movimientoServicio.ultimosMovimientosPorUsuario(usuario.getId(), 5);

        // Agregar al modelo
        model.addAttribute("ingresos", ingresos);
        model.addAttribute("gastos", gastos);
        model.addAttribute("balance", balance);
        model.addAttribute("metaAhorro", usuario.getMetaAhorro());
        model.addAttribute("ahorroActual", balance);
        model.addAttribute("ultimosMovimientos", movimientos);

        return "usuario_dashboard";
    }

    // 🌟 PERFIL DEL USUARIO (con edición de datos)
    @GetMapping("/perfil")
    public String verPerfil(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof UsuarioDetails) {
            UsuarioDetails userDetails = (UsuarioDetails) auth.getPrincipal();
            Usuario usuario = userDetails.getUsuario();
            model.addAttribute("usuario", usuario);
        }
        return "perfil";
    }

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

    // 🌟 LISTADO DE USUARIOS (solo ADMIN)
    @GetMapping("/usuarios")
    public String mostrarUsuarios(Model model) {
        List<Usuario> usuarios = usuarioServicio.listarUsuarios();
        model.addAttribute("usuarios", usuarios);
        return "usuarios";
    }

    // 🌟 GUARDAR USUARIO (registro)
    @PostMapping("/usuarios/guardar")
    public String guardarUsuario(@ModelAttribute("usuario") Usuario usuario) {
        usuario.setRol("USER");
        usuario.setActivo(true);
        usuarioServicio.guardarUsuario(usuario);
        return "redirect:/?creado=true";
    }

    // 🌟 FORMULARIO EDITAR USUARIO (ADMIN)
    @GetMapping("/usuarios/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") Long id, Model model) {
        Usuario usuario = usuarioServicio.obtenerUsuarioPorId(id)
            .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        model.addAttribute("usuario", usuario);
        return "nuevo_usuario";
    }

    // 🌟 ACTIVAR / DESACTIVAR USUARIO
    @GetMapping("/usuarios/desactivar/{id}")
    public String desactivarUsuario(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        usuarioServicio.obtenerUsuarioPorId(id).ifPresent(usuario -> {
            usuario.setActivo(false);
            usuarioServicio.guardarUsuario(usuario);
            redirectAttributes.addFlashAttribute("mensajeExito", "Usuario desactivado correctamente.");
        });
        return "redirect:/usuarios";
    }

    @GetMapping("/usuarios/activar/{id}")
    public String activarUsuario(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        usuarioServicio.obtenerUsuarioPorId(id).ifPresent(usuario -> {
            usuario.setActivo(true);
            usuarioServicio.guardarUsuario(usuario);
            redirectAttributes.addFlashAttribute("mensajeExito", "Usuario activado correctamente.");
        });
        return "redirect:/usuarios";
    }

    // 🌟 ACTUALIZAR META DE AHORRO (solo para USER)
    @PostMapping("/usuario/actualizar-meta")
    public String actualizarMeta(@RequestParam("nuevaMeta") Double nuevaMeta,
                                 Principal principal,
                                 RedirectAttributes redirect) {
        Usuario usuario = usuarioServicio.buscarPorCorreo(principal.getName());
        usuario.setMetaAhorro(nuevaMeta);
        usuarioServicio.guardarUsuario(usuario);
        redirect.addFlashAttribute("mensajeExito", "Meta de ahorro actualizada con éxito");
        return "redirect:/usuario";
    }
}

