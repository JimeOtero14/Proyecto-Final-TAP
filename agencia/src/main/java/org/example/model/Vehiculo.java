package org.example.model;

import javafx.scene.image.Image;
import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.sql.Blob;
import javax.sql.rowset.serial.SerialBlob;

public class Vehiculo {
    private String idVehiculo;
    private String nombre;
    private String modelo;
    private int año;
    private String marca;
    private String paisOrigen;
    private BigDecimal kilometraje;
    private String estadoVehiculo;
    private String noSerie;
    private String color;
    private BigDecimal precioCompra;
    private BigDecimal precioVenta;
    private LocalDate fechaVenta;
    private LocalDate fechaIngreso;
    private String descripcion;
    private String tipoCombustible;
    private String capacidad;
    private String tipoVehiculo;
    private String transmision;
    private Blob foto;
    private String idProveedor;

    public Vehiculo() {

    }

    public Vehiculo(String idVehiculo, String nombre, String modelo, int año,
                    String marca, String color, BigDecimal precioVenta,
                    String estadoVehiculo, String idProveedor) {
        this.idVehiculo = idVehiculo;
        this.nombre = nombre;
        this.modelo = modelo;
        this.año = año;
        this.marca = marca;
        this.color = color;
        this.precioVenta = precioVenta;
        this.estadoVehiculo = estadoVehiculo;
        this.idProveedor = idProveedor;
        this.fechaIngreso = LocalDate.now();
    }

    public String getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(String idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getPaisOrigen() {
        return paisOrigen;
    }

    public void setPaisOrigen(String paisOrigen) {
        this.paisOrigen = paisOrigen;
    }

    public BigDecimal getKilometraje() {
        return kilometraje;
    }

    public void setKilometraje(BigDecimal kilometraje) {
        this.kilometraje = kilometraje;
    }

    public String getEstadoVehiculo() {
        return estadoVehiculo;
    }

    public void setEstadoVehiculo(String estadoVehiculo) {
        this.estadoVehiculo = estadoVehiculo;
    }

    public String getNoSerie() {
        return noSerie;
    }

    public void setNoSerie(String noSerie) {
        this.noSerie = noSerie;
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

    public LocalDate getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(LocalDate fechaVenta) {
        this.fechaVenta = fechaVenta;
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

    public String getTipoCombustible() {
        return tipoCombustible;
    }

    public void setTipoCombustible(String tipoCombustible) {
        this.tipoCombustible = tipoCombustible;
    }

    public String getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(String capacidad) {
        this.capacidad = capacidad;
    }

    public String getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(String tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public String getTransmision() {
        return transmision;
    }

    public void setTransmision(String transmision) {
        this.transmision = transmision;
    }

    public Blob getFoto() {
        return foto;
    }

    public void setFoto(Blob foto) {
        this.foto = foto;
    }

    public String getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(String idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getNombreCompleto() {
        return marca + " " + modelo + " " + año;
    }

    public String getInformacionBasica() {
        return String.format("%s %s %d - %s", marca, modelo, año, color);
    }

    public boolean isDisponible() {
        return "Disponible".equalsIgnoreCase(estadoVehiculo);
    }

    public boolean isVendido() {
        return "Vendido".equalsIgnoreCase(estadoVehiculo);
    }

    public boolean isReservado() {
        return "Reservado".equalsIgnoreCase(estadoVehiculo);
    }

    public boolean isEnMantenimiento() {
        return "Mantenimiento".equalsIgnoreCase(estadoVehiculo);
    }

    public BigDecimal calcularMargenGanancia() {
        if (precioCompra != null && precioVenta != null) {
            return precioVenta.subtract(precioCompra);
        }
        return BigDecimal.ZERO;
    }

    public BigDecimal calcularPorcentajeGanancia() {
        if (precioCompra != null && precioVenta != null &&
                precioCompra.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal ganancia = calcularMargenGanancia();
            return ganancia.divide(precioCompra, 4, BigDecimal.ROUND_HALF_UP)
                    .multiply(new BigDecimal("100"));
        }
        return BigDecimal.ZERO;
    }

    public int getEdadVehiculo() {
        if (año > 0) {
            return LocalDate.now().getYear() - año;
        }
        return 0;
    }

    public String getEstadoFormateado() {
        if (isDisponible()) return "Disponible";
        if (isVendido()) return "Vendido";
        if (isReservado()) return "Reservado";
        if (isEnMantenimiento()) return "En Mantenimiento";
        return estadoVehiculo;
    }

    public void cargarFotoDesdeArchivo(File archivo) throws IOException {
        if (archivo != null && archivo.exists()) {
            try (FileInputStream fis = new FileInputStream(archivo)) {
                byte[] bytes = fis.readAllBytes();
                this.foto = new SerialBlob(bytes);
            } catch (Exception e) {
                throw new IOException("Error al crear SerialBlob: " + e.getMessage(), e);
            }
        }
    }

    public Image getFotoComoImage() {
        if (foto != null) {
            try {
                byte[] bytes = foto.getBytes(1, (int) foto.length());
                return new Image(new ByteArrayInputStream(bytes));
            } catch (Exception e) {
                System.err.println("Error al convertir foto: " + e.getMessage());
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return String.format("Vehiculo{id='%s', %s %s %d, precio=$%s, estado='%s'}",
                idVehiculo, marca, modelo, año,
                precioVenta != null ? precioVenta.toString() : "0",
                estadoVehiculo);
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

    public String[] toTableRow() {
        return new String[] {
                idVehiculo,
                marca,
                modelo,
                String.valueOf(año),
                color,
                precioVenta != null ? "$" + precioVenta.toString() : "$0",
                estadoVehiculo,
                kilometraje != null ? kilometraje.toString() + " km" : "0 km",
                tipoVehiculo != null ? tipoVehiculo : "N/A"
        };
    }

    public boolean isValid() {
        return idVehiculo != null && !idVehiculo.trim().isEmpty() &&
                marca != null && !marca.trim().isEmpty() &&
                modelo != null && !modelo.trim().isEmpty() &&
                año > 1900 && año <= LocalDate.now().getYear() + 1;
    }

    public String getResumen() {
        return String.format(
                "ID: %s | %s %s %d | Color: %s | Estado: %s | Precio: $%s",
                idVehiculo,
                marca,
                modelo,
                año,
                color != null ? color : "N/A",
                estadoVehiculo != null ? estadoVehiculo : "N/A",
                precioVenta != null ? precioVenta.toString() : "0"
        );
    }
}