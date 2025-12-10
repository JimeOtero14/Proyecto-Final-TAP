package org.example.model;

public class Proveedor {
    private String idProveedor;
    private String nombre;
    private String telefono;
    private String direccion;
    private String rfc;

    private String estado;

    public Proveedor() {}

    public Proveedor(String idProveedor, String nombre, String telefono, String direccion, String rfc, String estado) {
        this.idProveedor = idProveedor;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.rfc = rfc;
        this.estado = estado;
    }

    public String getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(String idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}