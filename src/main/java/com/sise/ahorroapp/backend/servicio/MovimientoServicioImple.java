package com.sise.ahorroapp.backend.servicio;

import com.sise.ahorroapp.backend.entidad.Movimiento;
import com.sise.ahorroapp.backend.entidad.Usuario;
import com.sise.ahorroapp.backend.repositorio.MovimientoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MovimientoServicioImple implements MovimientoServicio {

    @Autowired
    private MovimientoRepositorio movimientoRepository;
    
    @Override
    public List<Movimiento> ultimosMovimientosPorUsuario(Long usuarioId, int limite) {
        return movimientoRepository.findTopNByUsuarioOrderByFechaDesc(usuarioId, PageRequest.of(0, limite));
    }


    @Override
    public Movimiento guardarMovimiento(Movimiento movimiento) {
        return movimientoRepository.save(movimiento);
    }

    @Override
    public List<Movimiento> listarMovimientos() {
        return movimientoRepository.findAll();
    }
    @Override
    public Optional<Movimiento> obtenerMovimientoPorId(Long id) {
        return movimientoRepository.findById(id);
    }

    @Override
    public void eliminarMovimiento(Long id) {
        movimientoRepository.deleteById(id);
    }
    
    @Override
    public List<Movimiento> listarPorUsuario(Usuario usuario) {
        return movimientoRepository.findByUsuario(usuario);
    }
    
    @Override
    public double obtenerTotalPorTipoYUsuario(String tipo, Usuario usuario) {
        Double total = movimientoRepository.sumMontoByTipoAndUsuario(tipo, usuario);
        return total != null ? total : 0.0;
    }
    
    @Override
    public long contarMovimientos() {
        return movimientoRepository.count();
    }
    
    @Override
    public Double totalIngresosDelMes() {
        LocalDate inicioMes = LocalDate.now().withDayOfMonth(1);
        LocalDate finMes = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());
        return movimientoRepository.totalPorTipoYFecha("INGRESO", inicioMes, finMes).orElse(0.0);
    }

    @Override
    public Double totalGastosDelMes() {
        LocalDate inicioMes = LocalDate.now().withDayOfMonth(1);
        LocalDate finMes = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());
        return movimientoRepository.totalPorTipoYFecha("GASTO", inicioMes, finMes).orElse(0.0);
    }
    
    @Override
    public void guardar(Movimiento movimiento) {
        movimientoRepository.save(movimiento);
    }
}