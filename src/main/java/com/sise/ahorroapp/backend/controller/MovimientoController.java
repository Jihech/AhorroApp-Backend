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
    private UsuarioServicio usuarioService; // ✅ ahora sí se inyecta correctamente

    @PostMapping
    public ResponseEntity<Movimiento> crearMovimiento(@RequestBody Movimiento movimiento) {
        // ✅ Suplantar temporalmente el usuario con ID 1L
        Optional<Usuario> optionalUsuario = usuarioService.obtenerUsuarioPorId(1L); // Ajusta el ID según tu base

        if (optionalUsuario.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        movimiento.setUsuario(optionalUsuario.get());

        Movimiento guardado = movimientoService.guardarMovimiento(movimiento);
        return ResponseEntity.ok(guardado);
    }





    @GetMapping
    public List<Movimiento> listarMovimientos() {
        return movimientoService.listarMovimientos();
    }

    @GetMapping("/{id}")
    public Movimiento obtenerMovimiento(@PathVariable Long id) {
        return movimientoService.obtenerMovimientoPorId(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMovimiento(@PathVariable Long id) {
        movimientoService.eliminarMovimiento(id);
        return ResponseEntity.noContent().build();
    }
}

