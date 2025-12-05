package org.example.model;

import java.math.BigDecimal;
import java.time.LocalDate;
public class Vehiculo {
    private String idVehiculo;
    private String marca;
    private String color;
    private BigDecimal precioCompra;
    private BigDecimal precioVenta;
    private LocalDate fechaIngreso;
    private String descripcion;

    // Constructor vacío
    public Vehiculo() {}

        this.idVehiculo = idVehiculo;
        this.marca = marca;
        this.color = color;
        this.precioVenta = precioVenta;
    }

    // Getters y Setters
    public String getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(String idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    }

    }

    }

    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public BigDecimal getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(BigDecimal precioCompra) {
        this.precioCompra = precioCompra;
    }

    public BigDecimal getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(BigDecimal precioVenta) {
        this.precioVenta = precioVenta;
    }

    }

    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    }

    }

    // Métodos útiles
    public String getNombreCompleto() {
    }

    public String getInformacionBasica() {
    }

    public boolean isDisponible() {
    }

    public boolean isVendido() {
    }

    public BigDecimal calcularMargenGanancia() {
        if (precioCompra != null && precioVenta != null) {
            return precioVenta.subtract(precioCompra);
        }
        return BigDecimal.ZERO;
    }

        if (precioCompra != null && precioVenta != null &&
                precioCompra.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal ganancia = calcularMargenGanancia();
            return ganancia.divide(precioCompra, 4, BigDecimal.ROUND_HALF_UP)
    }
    }

    @Override
    public String toString() {
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Vehiculo vehiculo = (Vehiculo) obj;
        return idVehiculo != null && idVehiculo.equals(vehiculo.idVehiculo);
    }

    @Override
    public int hashCode() {
        return idVehiculo != null ? idVehiculo.hashCode() : 0;
    }
}