package com.sise.ahorroapp.backend.entidad;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "deudas")
public class Deuda {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private BigDecimal monto; // Monto total de la deuda
	private BigDecimal montoAbonado; // Monto abonado hasta ahora
	private String descripcion;
	private LocalDate fechaRegistro;
	private LocalDate fechaLimite;

	@Column(nullable = false)
	private Boolean pagada = false; // true = pagada, false = pendiente

	private String acreedor;
	private String prioridad; // Opcional: "Alta", "Media", "Baja"
	private String notas;

	private Boolean estado;
	
	

	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;

	// Constructor vacío
	public Deuda() {
		this.montoAbonado = BigDecimal.ZERO;
		this.fechaRegistro = LocalDate.now();
		this.pagada = false;
	}
	public String getFechaLimiteFormateada() {
	    if (fechaLimite == null) return "";
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	    return fechaLimite.format(formatter);
	}

	// Constructor con campos esenciales
	public Deuda(BigDecimal monto, String descripcion, LocalDate fechaLimite, Usuario usuario) {
		this.monto = monto;
		this.montoAbonado = BigDecimal.ZERO;
		this.descripcion = descripcion;
		this.fechaRegistro = LocalDate.now();
		this.fechaLimite = fechaLimite;
		this.pagada = false;
		this.usuario = usuario;
	}

	// Monto restante por pagar
	public BigDecimal getMontoRestante() {
		if (monto == null || montoAbonado == null)
			return BigDecimal.ZERO;
		return monto.subtract(montoAbonado);
	}

	// Getters y setters

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

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

	public Boolean getPagada() {
		return pagada;
	}

	public void setPagada(Boolean pagada) {
		this.pagada = pagada;
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
