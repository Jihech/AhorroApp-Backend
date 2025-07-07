package com.sise.ahorroapp.backend.servicio;

import com.sise.ahorroapp.backend.entidad.Usuario;
import java.util.Optional;
import java.util.List;

public interface UsuarioServicio {
    Usuario guardarUsuario(Usuario usuario);
    List<Usuario> listarUsuarios();
    Optional<Usuario> obtenerUsuarioPorId(Long id);
    void eliminarUsuario(Long id);
    Optional<Usuario> obtenerPorCorreo(String correo);
    List<Usuario> listarUsuariosActivos();
	Usuario buscarPorCorreo(String correo);
	long contarUsuarios();


}