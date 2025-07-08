package com.sise.ahorroapp.backend.controller;

import com.sise.ahorroapp.backend.entidad.Movimiento;
import com.sise.ahorroapp.backend.entidad.Usuario;
import com.sise.ahorroapp.backend.servicio.MovimientoServicio;
import com.sise.ahorroapp.backend.servicio.UsuarioServicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/movimientos")
public class MovimientoController {

    @Autowired
    private MovimientoServicio movimientoService;

    @Autowired
    private UsuarioServicio usuarioService;

    // ✅ Crear movimiento asociando correctamente al usuario recibido desde Android
    @PostMapping
    public ResponseEntity<Movimiento> crearMovimiento(@RequestBody Movimiento movimiento) {
        // Validar si el objeto usuario viene con ID
        if (movimiento.getUsuario() == null || movimiento.getUsuario().getId() == null) {
            return ResponseEntity.badRequest().body(null);
        }

        // Buscar usuario por ID
        Optional<Usuario> usuarioOpt = usuarioService.obtenerUsuarioPorId(movimiento.getUsuario().getId());
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Asociar usuario real al movimiento antes de guardar
        movimiento.setUsuario(usuarioOpt.get());
        Movimiento guardado = movimientoService.guardarMovimiento(movimiento);
        return ResponseEntity.ok(guardado);
    }

    // 🔍 Listar todos los movimientos (⚠️ Solo para pruebas/admin)
    @GetMapping
    public ResponseEntity<List<Movimiento>> listarMovimientos() {
        List<Movimiento> movimientos = movimientoService.listarMovimientos();
        return ResponseEntity.ok(movimientos);
    }

    // 🔍 Obtener un movimiento por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Movimiento> obtenerMovimiento(@PathVariable Long id) {
        return movimientoService.obtenerMovimientoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 🗑️ Eliminar un movimiento por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMovimiento(@PathVariable Long id) {
        movimientoService.eliminarMovimiento(id);
        return ResponseEntity.noContent().build();
    }

    // 📄 Listar movimientos por ID de usuario
    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<Movimiento>> listarMovimientosPorUsuario(@PathVariable Long id) {
        Optional<Usuario> usuarioOpt = usuarioService.obtenerUsuarioPorId(id);
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        List<Movimiento> movimientos = movimientoService.listarPorUsuario(usuarioOpt.get());
        return ResponseEntity.ok(movimientos);
    }
}

