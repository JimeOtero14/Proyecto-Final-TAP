package org.example.controller;

import org.example.database.DatabaseConnection;
import org.example.model.Empleado;
import org.example.view.LoginView;
import org.example.view.MainView;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class MainController {
    private Empleado empleadoActual;
    private MainView vistaPrincipal;

    public MainController(Empleado empleado) {
        this.empleadoActual = empleado;
        this.vistaPrincipal = new MainView(this, empleado);
    }

    public Empleado getEmpleadoActual() {
        return empleadoActual;
    }

    public void cerrarSesion(Stage stageActual) {
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Cerrar Sesión");
        confirmacion.setHeaderText("¿Está seguro que desea cerrar sesión?");
        confirmacion.setContentText("Presione OK para confirmar");

        confirmacion.showAndWait().ifPresent(respuesta -> {
            if (respuesta == ButtonType.OK) {
                DatabaseConnection.closeConnection();
                stageActual.close();
                mostrarLogin();
            }
        });
    }

    private void mostrarLogin() {
        Stage ventanaLogin = new Stage();
        LoginView vistaLogin = new LoginView();

        Scene escena = new Scene(vistaLogin.getRoot(), 600, 400);
        ventanaLogin.setScene(escena);
        ventanaLogin.setTitle("Agencia de Autos - Login");
        ventanaLogin.setResizable(false);

        ventanaLogin.setOnHidden(e -> {
            DatabaseConnection.closeConnection();
        });

        ventanaLogin.show();
    }

    public void mostrarRegistrarVehiculo() {
        System.out.println("Mostrar registro de vehículo para: " + empleadoActual.getNombreCompleto());
    }

    public void mostrarConsultarInventario() {
        System.out.println("Consultar inventario");
    }

    public void mostrarGestionarCliente() {
        System.out.println("Gestionar cliente");
    }

    public void mostrarProcesarVenta() {
        System.out.println("Procesar venta");
    }

    public void mostrarConfigurarPrecios() {
        System.out.println("Configurar precios");
    }

    public void mostrarGestionUsuarios() {
        System.out.println("Gestión de usuarios");
    }

    public MainView getVista() {
        return vistaPrincipal;
    }
}