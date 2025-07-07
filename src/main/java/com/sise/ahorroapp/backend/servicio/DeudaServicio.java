package com.sise.ahorroapp.backend.servicio;

import com.sise.ahorroapp.backend.entidad.Deuda;
import com.sise.ahorroapp.backend.entidad.Usuario;

import java.util.List;

public interface DeudaServicio {
    
    void guardar(Deuda deuda);                            // ➕ Guardar o actualizar
   // List<Deuda> listarPorUsuario(Usuario usuario);         // 📋 Listar deudas de un usuario
    Deuda buscarPorId(Long id);                           // 🔍 Buscar una deuda por ID
    void eliminar(Long id);   
    List<Deuda> listarPorUsuario(Long usuarioId);// 🗑 Eliminar una deuda
}
