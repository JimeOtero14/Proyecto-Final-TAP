package org.example.dao;

import org.example.model.Mantenimiento;
import java.util.List;

public interface MantenimientoDAO {
    boolean registrarMantenimiento(Mantenimiento mantenimiento);
    List<Mantenimiento> listarPorVehiculo(String idVehiculo);
    List<Mantenimiento> listarTodos();
    boolean actualizarMantenimiento(Mantenimiento mantenimiento);
    boolean eliminarMantenimiento(String idMantenimiento);
    Mantenimiento buscarPorId(String idMantenimiento);
}