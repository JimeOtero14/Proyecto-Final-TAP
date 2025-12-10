package org.example.model;

import java.sql.Blob;

public class Documento {
    private String idVehiculo;
    private String idArchivo;
    private String nombre;
    private Blob archivo;
    private String tipoDocumento;

    public Documento() {}

    public Documento(String idVehiculo, String idArchivo, String nombre, Blob archivo, String tipoDocumento) {
        this.idVehiculo = idVehiculo;
        this.idArchivo = idArchivo;
        this.nombre = nombre;
        this.archivo = archivo;
        this.tipoDocumento = tipoDocumento;
    }

    public String getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(String idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public String getIdArchivo() {
        return idArchivo;
    }

    public void setIdArchivo(String idArchivo) {
        this.idArchivo = idArchivo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Blob getArchivo() {
        return archivo;
    }

    public void setArchivo(Blob archivo) {
        this.archivo = archivo;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }
}