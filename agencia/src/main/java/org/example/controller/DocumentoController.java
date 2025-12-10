package org.example.controller;

import org.example.dao.DocumentoDAO;
import org.example.dao.DocumentoDAOImpl;
import org.example.dao.VehiculoDAO;
import org.example.dao.VehiculoDAOImpl;
import org.example.model.Documento;
import org.example.model.Vehiculo;
import org.example.model.Empleado;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import org.example.view.GerenteView;

import java.sql.Blob;
import java.util.List;

public class DocumentoController {
    private DocumentoDAO documentoDAO;
    private VehiculoDAO vehiculoDAO;
    private Empleado gerenteActual;

    public DocumentoController(Empleado gerente) {
        this.documentoDAO = new DocumentoDAOImpl();
        this.vehiculoDAO = new VehiculoDAOImpl();
        this.gerenteActual = gerente;
    }

    public boolean registrarDocumento(String idVehiculo, String idArchivo, String nombre, Blob archivo, String tipoDocumento) {
        if (idVehiculo == null || idVehiculo.trim().isEmpty()) {
            mostrarAlerta("Error", "ID Vehículo es requerido", Alert.AlertType.ERROR);
            return false;
        }

        if (idArchivo == null || idArchivo.trim().isEmpty()) {
            mostrarAlerta("Error", "ID Archivo es requerido", Alert.AlertType.ERROR);
            return false;
        }

        if (nombre == null || nombre.trim().isEmpty()) {
            mostrarAlerta("Error", "Nombre es requerido", Alert.AlertType.ERROR);
            return false;
        }

        if (archivo == null) {
            mostrarAlerta("Error", "Archivo PDF es requerido", Alert.AlertType.ERROR);
            return false;
        }

        Documento documento = new Documento();
        documento.setIdVehiculo(idVehiculo);
        documento.setIdArchivo(idArchivo);
        documento.setNombre(nombre);
        documento.setArchivo(archivo);
        documento.setTipoDocumento(tipoDocumento);

        boolean exito = documentoDAO.registrarDocumento(documento);

        if (exito) {
            mostrarAlerta("Éxito", "Documento registrado correctamente", Alert.AlertType.INFORMATION);
            return true;
        } else {
            mostrarAlerta("Error", "No se pudo registrar el documento", Alert.AlertType.ERROR);
            return false;
        }
    }

    public List<Vehiculo> obtenerVehiculos() {
        return vehiculoDAO.listarTodos();
    }

    public void volverAGerenteView(Stage ventanaActual) {
        ventanaActual.close();
        GerenteView vistaGerente = new GerenteView(new LoginController(), gerenteActual);
        Stage nuevoStage = new Stage();
        nuevoStage.setScene(new javafx.scene.Scene(vistaGerente.getRoot(), 800, 600));
        nuevoStage.setTitle("Gerente - " + gerenteActual.getNombreCompleto());
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