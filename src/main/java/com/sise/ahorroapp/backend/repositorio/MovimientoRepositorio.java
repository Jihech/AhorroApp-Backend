package com.sise.ahorroapp.backend.repositorio;

import com.sise.ahorroapp.backend.entidad.Movimiento;

import com.sise.ahorroapp.backend.entidad.Usuario;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

@Repository
public interface MovimientoRepositorio extends JpaRepository<Movimiento, Long> {
	 List<Movimiento> findByUsuario(Usuario usuario);
	 @Query("SELECT SUM(m.monto) FROM Movimiento m WHERE m.usuario = :usuario AND m.tipo = :tipo")
	 Double sumMontoByTipoAndUsuario(@Param("tipo") String tipo, @Param("usuario") Usuario usuario);
	 
	 @Query("SELECT m FROM Movimiento m WHERE m.usuario.id = :usuarioId ORDER BY m.fecha DESC")
	 List<Movimiento> findTopNByUsuarioOrderByFechaDesc(@Param("usuarioId") Long usuarioId, Pageable pageable);
	 
	 @Query("SELECT SUM(m.monto) FROM Movimiento m WHERE m.tipo = :tipo AND m.fecha BETWEEN :inicio AND :fin")
	 Optional<Double> totalPorTipoYFecha(@Param("tipo") String tipo, @Param("inicio") LocalDate inicio, @Param("fin") LocalDate fin);


}

