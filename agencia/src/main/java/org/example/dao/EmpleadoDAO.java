package org.example.dao;

import org.example.model.Empleado;
import java.util.List;

public interface EmpleadoDAO {
    Empleado autenticar(String usuario, String contrasena);
    boolean registrarEmpleado(Empleado empleado);
    Empleado buscarPorId(String idEmpleado);
    List<Empleado> listarTodos();
    boolean actualizarEmpleado(Empleado empleado);
    boolean eliminarEmpleado(String idEmpleado);
    List<Empleado> listarActivos();
    boolean cambiarEstadoEmpleado(String idEmpleado, String nuevoEstado);
}