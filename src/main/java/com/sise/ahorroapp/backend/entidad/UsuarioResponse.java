package com.sise.ahorroapp.backend.entidad;

public class UsuarioResponse {
    private Long id;
    private String nombre;
    private String correo;
    private Boolean activo;

    // Constructor vacío
    public UsuarioResponse() {}

    // Constructor con parámetros
    public UsuarioResponse(Long id, String nombre, String correo, Boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.activo = activo;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

    // Getters y setters
}