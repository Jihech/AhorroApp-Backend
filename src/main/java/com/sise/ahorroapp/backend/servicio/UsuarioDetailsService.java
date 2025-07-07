package com.sise.ahorroapp.backend.servicio;

import com.sise.ahorroapp.backend.entidad.Usuario;
import com.sise.ahorroapp.backend.repositorio.UsuarioRepositorio;
import com.sise.ahorroapp.backend.seguridad.UsuarioDetails;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        try {
            Optional<Usuario> usuarioOpt = usuarioRepositorio.findByCorreo(correo);

            if (!usuarioOpt.isPresent()) {
                throw new UsernameNotFoundException("Correo no encontrado: " + correo);
            }

            Usuario usuario = usuarioOpt.get();

            System.out.println("🟢 Usuario encontrado: " + usuario.getCorreo());
            System.out.println("🟢 Contraseña en BD: " + usuario.getClave());
            System.out.println("🟢 Activo? " + usuario.getActivo());

            if (!usuario.getActivo()) {
                throw new UsernameNotFoundException("Usuario inactivo: " + correo);
            }

            // 👇 Usamos la clase personalizada que implementa UserDetails
            return new UsuarioDetails(usuario);

        } catch (Exception e) {
            System.out.println("❌ Error en loadUserByUsername: " + e.getMessage());
            e.printStackTrace();
            throw new UsernameNotFoundException("Fallo interno en login: " + e.getMessage());
        }
    }
    
    
    
}
