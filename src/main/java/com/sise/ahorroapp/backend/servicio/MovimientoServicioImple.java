package com.sise.ahorroapp.backend.servicio;

import com.sise.ahorroapp.backend.entidad.Movimiento;
import com.sise.ahorroapp.backend.entidad.Usuario;
import com.sise.ahorroapp.backend.repositorio.MovimientoRepositorio;

import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovimientoServicioImple implements MovimientoServicio {

    @Autowired
    private MovimientoRepositorio movimientoRepository;

    @Override
    public Movimiento guardarMovimiento(Movimiento movimiento) {
        return movimientoRepository.save(movimiento);
    }

    @Override
    public void guardar(Movimiento movimiento) {
        movimientoRepository.save(movimiento);
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
    public List<Movimiento> ultimosMovimientosPorUsuario(Long usuarioId) {
        return movimientoRepository.findTop5ByUsuarioIdOrderByFechaDesc(usuarioId);
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
    public Page<Movimiento> filtrarMovimientos(String tipo, Long usuarioId, LocalDate desde, LocalDate hasta, Pageable pageable) {
        return movimientoRepository.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (tipo != null && !tipo.isEmpty()) {
                predicates.add(cb.equal(root.get("tipo"), tipo));
            }

            if (usuarioId != null) {
                predicates.add(cb.equal(root.get("usuario").get("id"), usuarioId));
            }

            if (desde != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("fecha"), desde));
            }

            if (hasta != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("fecha"), hasta));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        }, pageable);
    }
    @Override
    public Page<Movimiento> listarMovimientosPorUsuario(Long usuarioId, Pageable pageable) {
        return movimientoRepository.findByUsuarioIdOrderByFechaDesc(usuarioId, pageable);
    }

}
