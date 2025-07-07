package com.sise.ahorroapp.backend.servicio;

import com.sise.ahorroapp.backend.entidad.Deuda;

import java.util.List;

public interface DeudaServicio {
    
    void guardar(Deuda deuda);                            // ➕ Guardar o actualizar
    List<Deuda> listarPorUsuario(Long usuarioId);         // 📋 Listar deudas de un usuario
    Deuda buscarPorId(Long id);                           // 🔍 Buscar una deuda por ID
    void eliminar(Long id);                               // 🗑 Eliminar una deuda
}
