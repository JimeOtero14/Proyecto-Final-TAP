package org.example.dao;

import org.example.model.Empleado;

public interface EmpleadoDAO {
    Empleado autenticar(String usuario, String contrasena);
}