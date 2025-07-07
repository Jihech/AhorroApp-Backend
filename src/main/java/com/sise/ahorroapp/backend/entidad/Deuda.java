package com.sise.ahorroapp.backend.entidad;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "deudas")
public class Deuda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal monto;              // Monto total de la deuda
    private BigDecimal montoAbonado;       // Monto que se ha abonado hasta ahora
    private String descripcion;
    private LocalDate fechaRegistro;
    private LocalDate fechaLimite;
    private Boolean estado;                // true = pagada, false = pendiente
    private String acreedor;
    private String prioridad;              // Opcional: "Alta", "Media", "Baja"
    private String notas;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    // 🔹 Constructor vacío
    public Deuda() {
        this.montoAbonado = BigDecimal.ZERO;
        this.estado = false;
        this.fechaRegistro = LocalDate.now();
    }

    // 🔹 Constructor con campos esenciales
    public Deuda(BigDecimal monto, String descripcion, LocalDate fechaLimite, Usuario usuario) {
        this.monto = monto;
        this.montoAbonado = BigDecimal.ZERO;
        this.descripcion = descripcion;
        this.fechaRegistro = LocalDate.now();
        this.fechaLimite = fechaLimite;
        this.estado = false;
        this.usuario = usuario;
    }

    // 🔹 Método auxiliar: cuánto falta por pagar
    public BigDecimal getMontoRestante() {
        if (monto == null || montoAbonado == null) return BigDecimal.ZERO;
        return monto.subtract(montoAbonado);
    }

    // ✅ Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public BigDecimal getMontoAbonado() {
        return montoAbonado;
    }

    public void setMontoAbonado(BigDecimal montoAbonado) {
        this.montoAbonado = montoAbonado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public LocalDate getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(LocalDate fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getAcreedor() {
        return acreedor;
    }

    public void setAcreedor(String acreedor) {
        this.acreedor = acreedor;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
