package com.sise.ahorroapp.backend.servicio;

import com.sise.ahorroapp.backend.entidad.Movimiento;
import com.sise.ahorroapp.backend.entidad.Usuario;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovimientoServicio {
    Movimiento guardarMovimiento(Movimiento movimiento);
    List<Movimiento> listarMovimientos();
    Optional<Movimiento> obtenerMovimientoPorId(Long id);
    void eliminarMovimiento(Long id);
    List<Movimiento> listarPorUsuario(Usuario usuario);
    List<Movimiento> ultimosMovimientosPorUsuario(Long usuarioId);
    double obtenerTotalPorTipoYUsuario(String tipo, Usuario usuario);
    long contarMovimientos();
    Double totalIngresosDelMes();
    Double totalGastosDelMes();
    void guardar(Movimiento movimiento);
    
    Page<Movimiento> listarMovimientosPorUsuario(Long usuarioId, Pageable pageable);

    Page<Movimiento> filtrarMovimientos(String tipo, Long usuarioId, LocalDate desde, LocalDate hasta, Pageable pageable);
}
