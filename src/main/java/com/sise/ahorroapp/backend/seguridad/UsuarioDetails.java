package com.sise.ahorroapp.backend.seguridad;

import com.sise.ahorroapp.backend.entidad.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UsuarioDetails implements UserDetails {

    private final Usuario usuario;

    public UsuarioDetails(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(usuario.getRol()));
    }


    @Override
    public String getPassword() {
        return usuario.getClave(); // Contraseña en la base de datos
    }

    @Override
    public String getUsername() {
        return usuario.getCorreo(); // Usuario se identifica con su correo
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Puedes personalizar esto si tienes un campo de expiración
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Personalizable si tienes "bloqueado"
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Igual, si manejas fechas de vencimiento de clave
    }

    @Override
    public boolean isEnabled() {
        return usuario.getActivo(); // Solo permite login si está activo
    }

    public Usuario getUsuario() {
        return usuario;
    }
}
