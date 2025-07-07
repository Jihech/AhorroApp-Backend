package com.sise.ahorroapp.backend.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sise.ahorroapp.backend.entidad.Movimiento;
import com.sise.ahorroapp.backend.entidad.Usuario;
import com.sise.ahorroapp.backend.servicio.MovimientoServicio;
import com.sise.ahorroapp.backend.servicio.UsuarioServicio;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller
public class HomeController {

	 @Autowired
	    private UsuarioServicio usuarioServicio;

	    @Autowired
	    private MovimientoServicio movimientoService;

	    @GetMapping("/inicio")
	    public String verDashboard(Model model) {
	    	
	    	 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    	    String correo = auth.getName(); // trae el correo del usuario logueado

	    	    Usuario usuario = usuarioServicio.obtenerPorCorreo(correo).orElse(null);
	    	    model.addAttribute("usuarioActual", usuario);

	    	    // Aquí va tu lógica de ingresos/gastos por usuario
	    	    // ...
	    	    
	        List<Usuario> usuarios = usuarioServicio.listarUsuarios();
	    	 // Total de usuarios
		        model.addAttribute("totalUsuarios", usuarios.size());
	        List<Movimiento> movimientos = movimientoService.listarMovimientos();

	        

	        // Total de ingresos
	        double totalIngresos = movimientos.stream()
	            .filter(m -> m.getTipo() != null && m.getTipo().trim().equalsIgnoreCase("ingreso"))
	            .mapToDouble(Movimiento::getMonto)
	            .sum();

	        // Total de gastos
	        double totalGastos = movimientos.stream()
	            .filter(m -> m.getTipo() != null && m.getTipo().trim().equalsIgnoreCase("gasto"))
	            .mapToDouble(Movimiento::getMonto)
	            .sum();

	        // Saldo actual
	        double saldoActual = totalIngresos - totalGastos;

	        model.addAttribute("totalIngresos", totalIngresos);
	        model.addAttribute("totalGastos", totalGastos);
	        model.addAttribute("saldoActual", saldoActual);
	        model.addAttribute("totalUsuarios", usuarioServicio.contarUsuarios());
	        model.addAttribute("totalMovimientos", movimientoService.contarMovimientos());
	        model.addAttribute("fechaActual", LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy", new Locale("es", "ES"))));

	        model.addAttribute("ingresosMes", movimientoService.totalIngresosDelMes());
	        model.addAttribute("gastosMes", movimientoService.totalGastosDelMes());

	        // Para el gráfico
	        Map<String, Double> ingresosPorUsuario = new HashMap<>();
	        Map<String, Double> gastosPorUsuario = new HashMap<>();

	        for (Usuario u : usuarios) {
	            double ingresos = movimientos.stream()
	                .filter(m -> m.getUsuario() != null)
	                .filter(m -> m.getUsuario().getId().equals(u.getId()) && m.getTipo().equalsIgnoreCase("ingreso"))
	                .mapToDouble(Movimiento::getMonto)
	                .sum();

	            double gastos = movimientos.stream()
	                .filter(m -> m.getUsuario() != null)
	                .filter(m -> m.getUsuario().getId().equals(u.getId()) && m.getTipo().equalsIgnoreCase("gasto"))
	                .mapToDouble(Movimiento::getMonto)
	                .sum();

	            ingresosPorUsuario.put(u.getNombre(), ingresos);
	            gastosPorUsuario.put(u.getNombre(), gastos);
	        }

	        model.addAttribute("ingresosPorUsuario", ingresosPorUsuario);
	        model.addAttribute("gastosPorUsuario", gastosPorUsuario);

	        return "usuario_dashboard";
	    }

	    @GetMapping("/index")
	    public String verInicio(Model model) {
	        model.addAttribute("usuario", new Usuario()); // 👈 agrega esto
	        return "index";
	    }
	    @GetMapping("/")
	    public String verInicioDesdeRaiz(Model model) {
	        model.addAttribute("usuario", new Usuario());
	        return "index";
	    }

	    

}
