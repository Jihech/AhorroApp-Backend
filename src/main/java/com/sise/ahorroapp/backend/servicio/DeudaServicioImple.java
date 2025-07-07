package com.sise.ahorroapp.backend.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sise.ahorroapp.backend.entidad.Deuda;
import com.sise.ahorroapp.backend.entidad.Usuario;
import com.sise.ahorroapp.backend.repositorio.DeudaRepositorio;
import com.sise.ahorroapp.backend.repositorio.UsuarioRepositorio;

@Service
public class DeudaServicioImple implements DeudaServicio {

    @Autowired
    private DeudaRepositorio deudaRepositorio;
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Override
    public void guardarDeuda(Deuda deuda) {
        deudaRepositorio.save(deuda);
    }

    @Override
    public List<Deuda> listarPorUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepositorio.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return deudaRepositorio.findByUsuario(usuario);
    }
    
    @Override
    public List<Deuda> findByUsuario(Long usuarioId) {
        return deudaRepositorio.findByUsuarioId(usuarioId);
    }


    @Override
    public Optional<Deuda> obtenerPorId(Long id) {
        return deudaRepositorio.findById(id);
    }
}
