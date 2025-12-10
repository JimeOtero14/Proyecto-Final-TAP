package org.example.controller;

import org.example.dao.VehiculoDAO;
import org.example.dao.VehiculoDAOImpl;
import org.example.model.Vehiculo;
import org.example.model.Empleado;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import org.example.view.MecanicoView;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.sql.Blob;

public class RegistroVehiculoController {
    private VehiculoDAO vehiculoDAO;
    private Empleado mecanicoActual;

    public RegistroVehiculoController(Empleado mecanico) {
        this.vehiculoDAO = new VehiculoDAOImpl();
        this.mecanicoActual = mecanico;
    }

    public boolean registrarVehiculo(
            String idVehiculo, String nombre, String modelo, int año,
            String marca, String paisOrigen, BigDecimal kilometraje,
            String estadoVehiculo, String noSerie, String color,
            BigDecimal precioCompra, BigDecimal precioVenta,
            LocalDate fechaVenta, LocalDate fechaIngreso,
            String descripcion, String tipoCombustible, String capacidad,
            String tipoVehiculo, String transmision, Blob foto,
            String idProveedor) {

        if (idVehiculo == null || idVehiculo.trim().isEmpty()) {
            mostrarAlerta("Error", "ID de vehículo es requerido", Alert.AlertType.ERROR);
            return false;
        }

        if (marca == null || marca.trim().isEmpty()) {
            mostrarAlerta("Error", "Marca es requerida", Alert.AlertType.ERROR);
            return false;
        }

        if (año < 1900 || año > LocalDate.now().getYear() + 1) {
            mostrarAlerta("Error", "Año inválido", Alert.AlertType.ERROR);
            return false;
        }

        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setIdVehiculo(idVehiculo);
        vehiculo.setNombre(nombre);
        vehiculo.setModelo(modelo);
        vehiculo.setAño(año);
        vehiculo.setMarca(marca);
        vehiculo.setPaisOrigen(paisOrigen);
        vehiculo.setKilometraje(kilometraje);
        vehiculo.setEstadoVehiculo(estadoVehiculo != null ? estadoVehiculo : "Disponible");
        vehiculo.setNoSerie(noSerie);
        vehiculo.setColor(color);
        vehiculo.setPrecioCompra(precioCompra);
        vehiculo.setPrecioVenta(precioVenta);
        vehiculo.setFechaVenta(fechaVenta);
        vehiculo.setFechaIngreso(fechaIngreso != null ? fechaIngreso : LocalDate.now());
        vehiculo.setDescripcion(descripcion);
        vehiculo.setTipoCombustible(tipoCombustible);
        vehiculo.setCapacidad(capacidad);
        vehiculo.setTipoVehiculo(tipoVehiculo);
        vehiculo.setTransmision(transmision);
        vehiculo.setFoto(foto);
        vehiculo.setIdProveedor(idProveedor);

        boolean exito = vehiculoDAO.registrarVehiculo(vehiculo);

        if (exito) {
            mostrarAlerta("Éxito", "Vehículo registrado correctamente", Alert.AlertType.INFORMATION);
            return true;
        } else {
            mostrarAlerta("Error", "No se pudo registrar el vehículo", Alert.AlertType.ERROR);
            return false;
        }
    }

    public void volverAMecanicoView(Stage ventanaActual) {
        ventanaActual.close();
        MecanicoView vistaMecanico = new MecanicoView(
                new LoginController(), mecanicoActual
        );
        Stage nuevoStage = new Stage();
        nuevoStage.setScene(new javafx.scene.Scene(vistaMecanico.getRoot(), 800, 600));
        nuevoStage.setTitle("Mecánico - " + mecanicoActual.getNombreCompleto());
        nuevoStage.show();
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}