package org.example.controller;

import org.example.dao.MantenimientoDAO;
import org.example.dao.MantenimientoDAOImpl;
import org.example.dao.VehiculoDAO;
import org.example.dao.VehiculoDAOImpl;
import org.example.model.Mantenimiento;
import org.example.model.Vehiculo;
import org.example.model.Empleado;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import org.example.view.MecanicoView;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class MantenimientoController {
    private MantenimientoDAO mantenimientoDAO;
    private VehiculoDAO vehiculoDAO;
    private Empleado mecanicoActual;

    public MantenimientoController(Empleado mecanico) {
        this.mantenimientoDAO = new MantenimientoDAOImpl();
        this.vehiculoDAO = new VehiculoDAOImpl();
        this.mecanicoActual = mecanico;
    }

    public boolean registrarMantenimiento(
            String idMantenimiento,
            String idVehiculo,
            LocalDate fechaMantenimiento,
            String tipoMantenimiento,
            String descripcion,
            BigDecimal costo,
            String estado,
            BigDecimal kilometrajeMomento,
            LocalDate proximoMantenimiento) {

        if (idMantenimiento == null || idMantenimiento.trim().isEmpty()) {
            mostrarAlerta("Error", "ID Mantenimiento es requerido", Alert.AlertType.ERROR);
            return false;
        }

        if (idVehiculo == null || idVehiculo.trim().isEmpty()) {
            mostrarAlerta("Error", "ID Vehículo es requerido", Alert.AlertType.ERROR);
            return false;
        }

        if (tipoMantenimiento == null || tipoMantenimiento.trim().isEmpty()) {
            mostrarAlerta("Error", "Tipo de mantenimiento es requerido", Alert.AlertType.ERROR);
            return false;
        }

        Mantenimiento mantenimiento = new Mantenimiento();
        mantenimiento.setIdMantenimiento(idMantenimiento);
        mantenimiento.setIdVehiculo(idVehiculo);
        mantenimiento.setFechaMantenimiento(fechaMantenimiento != null ? fechaMantenimiento : LocalDate.now());
        mantenimiento.setTipoMantenimiento(tipoMantenimiento);
        mantenimiento.setDescripcion(descripcion);
        mantenimiento.setCosto(costo != null ? costo : BigDecimal.ZERO);
        mantenimiento.setEstado(estado != null ? estado : "Completado");
        mantenimiento.setRealizadoPor(mecanicoActual.getIdEmpleado());
        mantenimiento.setKilometrajeMomento(kilometrajeMomento);
        mantenimiento.setProximoMantenimiento(proximoMantenimiento);

        boolean exito = mantenimientoDAO.registrarMantenimiento(mantenimiento);

        if (exito) {
            // Actualizar estado del vehículo si es necesario
            if ("En Progreso".equals(estado)) {
                actualizarEstadoVehiculo(idVehiculo, "Mantenimiento");
            }
            mostrarAlerta("Éxito", "Mantenimiento registrado correctamente", Alert.AlertType.INFORMATION);
            return true;
        } else {
            mostrarAlerta("Error", "No se pudo registrar el mantenimiento", Alert.AlertType.ERROR);
            return false;
        }
    }

    private void actualizarEstadoVehiculo(String idVehiculo, String nuevoEstado) {
        try {
            Vehiculo vehiculo = vehiculoDAO.buscarPorId(idVehiculo);
            if (vehiculo != null) {
                vehiculo.setEstadoVehiculo(nuevoEstado);
                vehiculoDAO.actualizarVehiculo(vehiculo);
            }
        } catch (Exception e) {
            System.err.println("Error al actualizar estado del vehículo: " + e.getMessage());
        }
    }

    public List<Vehiculo> obtenerVehiculos() {
        return vehiculoDAO.listarTodos();
    }

    public List<Mantenimiento> obtenerMantenimientosPorVehiculo(String idVehiculo) {
        return mantenimientoDAO.listarPorVehiculo(idVehiculo);
    }

    public Empleado getMecanicoActual() {
        return mecanicoActual;
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}