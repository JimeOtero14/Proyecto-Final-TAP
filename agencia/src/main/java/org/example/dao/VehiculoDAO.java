package org.example.dao;

import org.example.model.Vehiculo;
import java.util.List;

public interface VehiculoDAO {
    boolean registrarVehiculo(Vehiculo vehiculo);
    Vehiculo buscarPorId(String idVehiculo);
    List<Vehiculo> listarTodos();
    boolean actualizarVehiculo(Vehiculo vehiculo);
    boolean eliminarVehiculo(String idVehiculo);
}