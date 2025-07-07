package com.sise.ahorroapp.backend.controller;

import com.sise.ahorroapp.backend.entidad.Usuario;
import com.sise.ahorroapp.backend.entidad.UsuarioResponse;
import com.sise.ahorroapp.backend.servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*") // Para permitir peticiones desde Android
public class UsuarioController {

    @Autowired
    private UsuarioServicio usuarioService;

    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioService.listarUsuarios();
    }

    @GetMapping("/{id}")
    public Optional<Usuario> obtenerUsuario(@PathVariable Long id) {
        return usuarioService.obtenerUsuarioPorId(id);
    }

    @PostMapping("/registro")
    public Usuario crearUsuario(@RequestBody Usuario usuario) {
        return usuarioService.guardarUsuario(usuario);
    }

    @DeleteMapping("/{id}")
    public void eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> loginUsuario(@RequestBody Usuario loginRequest) {
        Optional<Usuario> usuarioOpt = usuarioService.obtenerPorCorreo(loginRequest.getCorreo());

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            if (usuario.getClave().equals(loginRequest.getClave())) {
                UsuarioResponse response = new UsuarioResponse(
                    usuario.getId(),
                    usuario.getNombre(),
                    usuario.getCorreo(),
                    usuario.getActivo()
                );
                return ResponseEntity.ok(response);
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
    }
    
}
