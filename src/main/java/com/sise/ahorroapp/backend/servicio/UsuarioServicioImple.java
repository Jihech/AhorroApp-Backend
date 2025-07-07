package com.sise.ahorroapp.backend.servicio;

import com.sise.ahorroapp.backend.entidad.Usuario;
import com.sise.ahorroapp.backend.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServicioImple implements UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Usuario guardarUsuario(Usuario usuario) {
        // Codifica la contraseña si se proporciona
        if (usuario.getClave() != null && !usuario.getClave().isBlank()) {
            usuario.setClave(passwordEncoder.encode(usuario.getClave()));
        }

        // Asigna metaAhorro por defecto SOLO si es USER y el valor está vacío
        if ("USER".equalsIgnoreCase(usuario.getRol()) && usuario.getMetaAhorro() == null) {
            usuario.setMetaAhorro(1000.0); // Puedes ajustar este valor por defecto
        }

        return usuarioRepository.save(usuario);
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Optional<Usuario> obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public Optional<Usuario> obtenerPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }

    @Override
    public Usuario buscarPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo).orElse(null);
    }

    @Override
    public List<Usuario> listarUsuariosActivos() {
        return usuarioRepository.findByActivoTrue();
    }
    
    @Override
    public long contarUsuarios() {
        return usuarioRepository.count();
    }

}

