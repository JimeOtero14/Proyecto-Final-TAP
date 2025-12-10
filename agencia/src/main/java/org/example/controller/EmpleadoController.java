package org.example.controller;

import org.example.dao.EmpleadoDAO;
import org.example.dao.EmpleadoDAOImpl;
import org.example.model.Empleado;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import java.math.BigDecimal;
import java.time.LocalDate;

public class EmpleadoController {
    private EmpleadoDAO empleadoDAO;

    public EmpleadoController() {
        this.empleadoDAO = new EmpleadoDAOImpl();
    }

    public boolean registrarEmpleado(
            String idEmpleado, String nombre, String apellidoPaterno,
            String apellidoMaterno, String puesto, String telefono,
            String email, BigDecimal salario, LocalDate fechaContratacion,
            String estado) {

        if (idEmpleado == null || idEmpleado.trim().isEmpty()) {
            mostrarAlerta("Error", "ID Empleado es requerido", Alert.AlertType.ERROR);
            return false;
        }

        if (nombre == null || nombre.trim().isEmpty()) {
            mostrarAlerta("Error", "Nombre es requerido", Alert.AlertType.ERROR);
            return false;
        }

        if (apellidoPaterno == null || apellidoPaterno.trim().isEmpty()) {
            mostrarAlerta("Error", "Apellido Paterno es requerido", Alert.AlertType.ERROR);
            return false;
        }

        if (fechaContratacion == null) {
            fechaContratacion = LocalDate.now();
        }

        if (estado == null || (!estado.equalsIgnoreCase("A") && !estado.equalsIgnoreCase("NA"))) {
            mostrarAlerta("Error", "Estado debe ser 'A' (Activo) o 'NA' (No Activo)", Alert.AlertType.ERROR);
            return false;
        }

        Empleado empleado = new Empleado();
        empleado.setIdEmpleado(idEmpleado);
        empleado.setNombre(nombre);
        empleado.setApellidoPaterno(apellidoPaterno);
        empleado.setApellidoMaterno(apellidoMaterno != null ? apellidoMaterno : "");
        empleado.setPuesto(puesto != null ? puesto : "");
        empleado.setTelefono(telefono != null ? telefono : "");
        empleado.setEmail(email != null ? email : "");
        empleado.setSalario(salario != null ? salario : BigDecimal.ZERO);
        empleado.setFechaContratacion(fechaContratacion);
        empleado.setEstado(estado.toUpperCase());

        boolean exito = empleadoDAO.registrarEmpleado(empleado);

        if (exito) {
            mostrarAlerta("Éxito", "Empleado registrado correctamente", Alert.AlertType.INFORMATION);
            return true;
        } else {
            mostrarAlerta("Error", "No se pudo registrar el empleado", Alert.AlertType.ERROR);
            return false;
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}