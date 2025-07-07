package com.sise.ahorroapp.backend.entidad;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "movimientos")
public class Movimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo; // "Ingreso" o "Gasto"
    private double monto;
    private String descripcion;
    private LocalDate fecha;

    // NUEVO: Monto de este ingreso que se usó para abonar a una deuda (si aplica)
    private Double montoAbonadoADeuda;

    // NUEVO: Relación con una deuda (solo si este ingreso se usó para abonar)
    @ManyToOne
    @JoinColumn(name = "deuda_id")
    private Deuda deudaAsociada;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    public Movimiento() {}

    public Movimiento(String tipo, double monto, String descripcion, LocalDate fecha) {
        this.tipo = tipo;
        this.monto = monto;
        this.descripcion = descripcion;
        this.fecha = fecha;
    }

    @PrePersist
    public void asignarFechaPorDefecto() {
        if (this.fecha == null) {
            this.fecha = LocalDate.now();
        }
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Double getMontoAbonadoADeuda() {
        return montoAbonadoADeuda;
    }

    public void setMontoAbonadoADeuda(Double montoAbonadoADeuda) {
        this.montoAbonadoADeuda = montoAbonadoADeuda;
    }

    public Deuda getDeudaAsociada() {
        return deudaAsociada;
    }

    public void setDeudaAsociada(Deuda deudaAsociada) {
        this.deudaAsociada = deudaAsociada;
    }
}
