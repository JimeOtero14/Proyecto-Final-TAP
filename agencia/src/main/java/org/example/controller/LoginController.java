package org.example.controller;


import org.example.model.Empleado;
import org.example.dao.EmpleadoDAO;
import org.example.dao.EmpleadoDAOImpl;
import org.example.view.*;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.sql.*;

public class LoginController {
    private EmpleadoDAO empleadoDAO;

    public LoginController() {
        this.empleadoDAO = new EmpleadoDAOImpl();
    }

    public Empleado autenticar(String usuario, String contrasena) {
        if (usuario == null || usuario.trim().isEmpty()) {
            System.err.println("Error: Usuario vacío");
            return null;
        }

        if (contrasena == null || contrasena.trim().isEmpty()) {
            System.err.println("Error: Contraseña vacía");
            return null;
        }
        return empleadoDAO.autenticar(usuario, contrasena);
    }

    public void redirigirSegunNivel(Empleado empleado, Stage ventanaLogin) {
        if (empleado == null) {
            System.err.println("Error: Empleado nulo");
            return;
        }

        ventanaLogin.close();
        Stage ventanaPrincipal = new Stage();

        switch (empleado.getPuesto().toUpperCase()) {
            case "ADMIN":
                mostrarVentanaAdmin(empleado, ventanaPrincipal);
                break;
            case "GERENTE":
                mostrarVentanaGerente(empleado, ventanaPrincipal);
                break;
            case "MECANICO":
                mostrarVentanaMecanico(empleado, ventanaPrincipal);
                break;
            case "VENDEDOR":
                mostrarVentanaVendedor(empleado, ventanaPrincipal);
                break;
            default:
                System.err.println("Nivel no reconocido: " + empleado.getPuesto());
                mostrarVentanaVendedor(empleado, ventanaPrincipal);
        }
    }

    private void mostrarVentanaAdmin(Empleado empleado, Stage stage) {
        AdminView vistaAdmin = new AdminView(this, empleado);
        Scene escena = new Scene(vistaAdmin.getRoot(), 800, 600);
        configurarVentana(stage, escena, "Administrador - " + empleado.getNombreCompleto());
    }

    private void mostrarVentanaGerente(Empleado empleado, Stage stage) {
        GerenteView vistaGerente = new GerenteView(this, empleado);
        Scene escena = new Scene(vistaGerente.getRoot(), 800, 600);
        configurarVentana(stage, escena, "Gerente - " + empleado.getNombreCompleto());
    }

    private void mostrarVentanaMecanico(Empleado empleado, Stage stage) {
        MecanicoView vistaMecanico = new MecanicoView(this, empleado);
        Scene escena = new Scene(vistaMecanico.getRoot(), 800, 600);
        configurarVentana(stage, escena, "Mecánico - " + empleado.getNombreCompleto());
    }

    private void mostrarVentanaVendedor(Empleado empleado, Stage stage) {
        VendedorView vistaVendedor = new VendedorView(this, empleado);
        Scene escena = new Scene(vistaVendedor.getRoot(), 800, 600);
        configurarVentana(stage, escena, "Vendedor - " + empleado.getNombreCompleto());
    }

    private void configurarVentana(Stage stage, Scene scene, String title) {
        stage.setScene(scene);
        stage.setTitle(title);
        stage.setResizable(false);
        stage.show();
    }

    public void cerrarSesion(Stage stageActual) {
        javafx.scene.control.Alert confirmacion = new javafx.scene.control.Alert(
                javafx.scene.control.Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Cerrar Sesión");
        confirmacion.setHeaderText("¿Está seguro que desea cerrar sesión?");
        confirmacion.setContentText("Presione OK para confirmar");

        confirmacion.showAndWait().ifPresent(respuesta -> {
            if (respuesta == javafx.scene.control.ButtonType.OK) {
                org.example.database.DatabaseConnection.closeConnection();
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
        ventanaLogin.show();
    }
}