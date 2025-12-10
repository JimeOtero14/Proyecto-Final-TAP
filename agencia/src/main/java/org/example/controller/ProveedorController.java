package org.example.controller;

import org.example.dao.ProveedorDAO;
import org.example.dao.ProveedorDAOImpl;
import org.example.model.Proveedor;
import org.example.model.Empleado;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import org.example.view.GerenteView;

public class ProveedorController {
    private ProveedorDAO proveedorDAO;
    private Empleado gerenteActual;

    public ProveedorController(Empleado gerente) {
        this.proveedorDAO = new ProveedorDAOImpl();
        this.gerenteActual = gerente;
    }

    public boolean registrarProveedor(String idProveedor, String nombre, String telefono, String direccion, String rfc) {
        if (idProveedor == null || idProveedor.trim().isEmpty()) {
            mostrarAlerta("Error", "ID Proveedor es requerido", Alert.AlertType.ERROR);
            return false;
        }

        if (nombre == null || nombre.trim().isEmpty()) {
            mostrarAlerta("Error", "Nombre es requerido", Alert.AlertType.ERROR);
            return false;
        }

        Proveedor proveedor = new Proveedor();
        proveedor.setIdProveedor(idProveedor);
        proveedor.setNombre(nombre);
        proveedor.setTelefono(telefono);
        proveedor.setDireccion(direccion);
        proveedor.setRfc(rfc);
        proveedor.setEstado("AC");

        boolean exito = proveedorDAO.registrarProveedor(proveedor);

        if (exito) {
            mostrarAlerta("Éxito", "Proveedor registrado correctamente", Alert.AlertType.INFORMATION);
            return true;
        } else {
            mostrarAlerta("Error", "No se pudo registrar el proveedor", Alert.AlertType.ERROR);
            return false;
        }
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