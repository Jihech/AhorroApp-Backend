package com.sise.ahorroapp.backend.servicio;

import java.util.List;
import java.util.Optional;

import com.sise.ahorroapp.backend.entidad.Deuda;


public interface DeudaServicio {
    void guardarDeuda(Deuda deuda);
    List<Deuda> listarPorUsuario(Long usuarioId);
    Optional<Deuda> obtenerPorId(Long id);
    List<Deuda> findByUsuario(Long usuarioId);


}
