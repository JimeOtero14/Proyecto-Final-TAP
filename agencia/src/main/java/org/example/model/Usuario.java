package org.example.model;

import java.time.LocalDate;

public class Usuario {
    private String idEmpleado;
    private String idUsuario;
    private String username;
    private String password;
    private LocalDate fechaCreacion;
    private String nivel;

    public Usuario() {}

    public Usuario(String idEmpleado, String username, String password, String nivel) {
        this.idEmpleado = idEmpleado;
        this.username = username;
        this.password = password;
        this.nivel = nivel;
        this.fechaCreacion = LocalDate.now();
    }

    public String getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(String idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getNivelFormateado() {
        if (nivel == null) return "";

        switch(nivel.toUpperCase()) {
            case "ADMIN": return "Administrador";
            case "GERENTE": return "Gerente";
            case "MECANICO": return "Mecánico";
            case "VENDEDOR": return "Vendedor";
            case "RH": return "Recursos Humanos";
            default: return nivel;
        }
    }

    @Override
    public String toString() {
        return username + " - " + getNivelFormateado();
    }
}