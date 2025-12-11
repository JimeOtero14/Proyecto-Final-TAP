package org.example.model;

import java.time.LocalDate;
import java.math.BigDecimal;

public class Mantenimiento {
    private String idMantenimiento;
    private String idVehiculo;
    private LocalDate fechaMantenimiento;
    private String tipoMantenimiento;
    private String descripcion;
    private BigDecimal costo;
    private String estado;
    private String realizadoPor; // ID del mecánico
    private BigDecimal kilometrajeMomento;
    private LocalDate proximoMantenimiento;

    public Mantenimiento() {}

    // Getters y Setters
    public String getIdMantenimiento() {
        return idMantenimiento;
    }

    public void setIdMantenimiento(String idMantenimiento) {
        this.idMantenimiento = idMantenimiento;
    }

    public String getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(String idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public LocalDate getFechaMantenimiento() {
        return fechaMantenimiento;
    }

    public void setFechaMantenimiento(LocalDate fechaMantenimiento) {
        this.fechaMantenimiento = fechaMantenimiento;
    }

    public String getTipoMantenimiento() {
        return tipoMantenimiento;
    }

    public void setTipoMantenimiento(String tipoMantenimiento) {
        this.tipoMantenimiento = tipoMantenimiento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getCosto() {
        return costo;
    }

    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getRealizadoPor() {
        return realizadoPor;
    }

    public void setRealizadoPor(String realizadoPor) {
        this.realizadoPor = realizadoPor;
    }

    public BigDecimal getKilometrajeMomento() {
        return kilometrajeMomento;
    }

    public void setKilometrajeMomento(BigDecimal kilometrajeMomento) {
        this.kilometrajeMomento = kilometrajeMomento;
    }

    public LocalDate getProximoMantenimiento() {
        return proximoMantenimiento;
    }

    public void setProximoMantenimiento(LocalDate proximoMantenimiento) {
        this.proximoMantenimiento = proximoMantenimiento;
    }
}