package org.example.dao;

import org.example.model.Proveedor;
import java.util.List;

public interface ProveedorDAO {
    boolean registrarProveedor(Proveedor proveedor);
    Proveedor buscarPorId(String idProveedor);
    List<Proveedor> listarTodos();
    boolean actualizarProveedor(Proveedor proveedor);
    boolean eliminarProveedor(String idProveedor);
}