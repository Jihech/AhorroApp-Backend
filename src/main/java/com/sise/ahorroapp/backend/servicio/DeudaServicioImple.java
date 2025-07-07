package com.sise.ahorroapp.backend.servicio;

import com.sise.ahorroapp.backend.entidad.Deuda;
import com.sise.ahorroapp.backend.repositorio.DeudaRepositorio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeudaServicioImple implements DeudaServicio {

    @Autowired
    private DeudaRepositorio deudaRepositorio;

    @Override
    public void guardar(Deuda deuda) {
        deudaRepositorio.save(deuda);
    }

    @Override
    public List<Deuda> listarPorUsuario(Long usuarioId) {
        return deudaRepositorio.findByUsuarioId(usuarioId);
    }

    @Override
    public Deuda buscarPorId(Long id) {
        return deudaRepositorio.findById(id).orElse(null);
    }

    @Override
    public void eliminar(Long id) {
        deudaRepositorio.deleteById(id);
    }
}
