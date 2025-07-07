package com.sise.ahorroapp.backend.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sise.ahorroapp.backend.entidad.Deuda;
import com.sise.ahorroapp.backend.entidad.Usuario;

@Repository
public interface DeudaRepositorio extends JpaRepository<Deuda, Long> {
	List<Deuda> findByUsuario(Usuario usuario);
	List<Deuda> findByUsuarioId(Long usuarioId);

}
