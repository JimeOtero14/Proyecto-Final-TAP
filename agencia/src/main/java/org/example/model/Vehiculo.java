package org.example.model;

import java.math.BigDecimal;
import java.time.LocalDate;
//lol
public class Vehiculo {
    private String idVehiculo;
    private String marca;
    private String modelo;
    private int anio;
    private String color;
    private String numeroSerie;
    private String numeroMotor;
    private String transmision;
    private String tipoCombustible;
    private int kilometraje;
    private String estado;
    private BigDecimal precioCompra;
    private BigDecimal precioVenta;
    private String tipoVehiculo;
    private int numeroPuertas;
    private String version;
    private LocalDate fechaIngreso;
    private String descripcion;
    private String imagenUrl;

    // Constructor vacío
    public Vehiculo() {}

    // Constructor con parámetros principales
    public Vehiculo(String idVehiculo, String marca, String modelo, int anio,
                    String color, BigDecimal precioVenta, String estado) {
        this.idVehiculo = idVehiculo;
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.color = color;
        this.precioVenta = precioVenta;
        this.estado = estado;
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

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getNumeroMotor() {
        return numeroMotor;
    }

    public void setNumeroMotor(String numeroMotor) {
        this.numeroMotor = numeroMotor;
    }

    public String getTransmision() {
        return transmision;
    }

    public void setTransmision(String transmision) {
        this.transmision = transmision;
    }

    public String getTipoCombustible() {
        return tipoCombustible;
    }

    public void setTipoCombustible(String tipoCombustible) {
        this.tipoCombustible = tipoCombustible;
    }

    public int getKilometraje() {
        return kilometraje;
    }

    public void setKilometraje(int kilometraje) {
        this.kilometraje = kilometraje;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

    public String getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(String tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public int getNumeroPuertas() {
        return numeroPuertas;
    }

    public void setNumeroPuertas(int numeroPuertas) {
        this.numeroPuertas = numeroPuertas;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    // Métodos útiles
    public String getNombreCompleto() {
        return marca + " " + modelo + " " + anio;
    }

    public String getInformacionBasica() {
        return String.format("%s %s %d - %s", marca, modelo, anio, color);
    }

    public boolean isDisponible() {
        return "Disponible".equalsIgnoreCase(estado);
    }

    public boolean isVendido() {
        return "Vendido".equalsIgnoreCase(estado);
    }

    public BigDecimal calcularMargenGanancia() {
        if (precioCompra != null && precioVenta != null) {
            return precioVenta.subtract(precioCompra);
        }
        return BigDecimal.ZERO;
    }

    public double calcularPorcentajeGanancia() {
        if (precioCompra != null && precioVenta != null &&
                precioCompra.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal ganancia = calcularMargenGanancia();
            return ganancia.divide(precioCompra, 4, BigDecimal.ROUND_HALF_UP)
                    .multiply(new BigDecimal("100"))
                    .doubleValue();
        }
        return 0.0;
    }

    @Override
    public String toString() {
        return String.format("Vehiculo{id='%s', %s, precio=$%s, estado='%s'}",
                idVehiculo, getNombreCompleto(), precioVenta, estado);
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