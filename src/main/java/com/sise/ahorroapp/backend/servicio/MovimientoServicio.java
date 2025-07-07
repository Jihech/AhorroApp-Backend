package com.sise.ahorroapp.backend.servicio;

import com.sise.ahorroapp.backend.entidad.Movimiento;
import com.sise.ahorroapp.backend.entidad.Usuario;

import java.util.List;
import java.util.Optional;

public interface MovimientoServicio {
    Movimiento guardarMovimiento(Movimiento movimiento);
    List<Movimiento> listarMovimientos();
    Optional<Movimiento> obtenerMovimientoPorId(Long id);  // Este
    void eliminarMovimiento(Long id);   
    double obtenerTotalPorTipoYUsuario(String tipo, Usuario usuario);
    List<Movimiento> ultimosMovimientosPorUsuario(Long usuarioId, int limite);
    List<Movimiento> listarPorUsuario(Usuario usuario); 
    long contarMovimientos();
    Double totalIngresosDelMes();
    Double totalGastosDelMes();
}
