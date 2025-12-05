package org.example.view;
//lol
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.controller.LoginController;
import org.example.database.DatabaseConnection;
import org.example.model.Empleado;

import java.lang.classfile.Label;

public class LoginView {
    private GridPane panelRaiz;
    private TextField campoUsuario;
    private PasswordField campoContrasena;
    private Button botonLogin;
    private Label etiquetaMensaje;
    private LoginController controlador;

    public LoginView() {
        controlador = new LoginController();
        crearVista();
        configurarEventos();
    }

    private void crearVista() {
        panelRaiz = new GridPane();
        panelRaiz.setPadding(new Insets(20));
        panelRaiz.setHgap(10);
        panelRaiz.setVgap(10);
        panelRaiz.setAlignment(Pos.CENTER);

        Label etiquetaTitulo = new Label("Sistema de Agencia de Autos");
        etiquetaTitulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        GridPane.setConstraints(etiquetaTitulo, 0, 0, 2, 1);

        Label etiquetaUsuario = new Label("Usuario:");
        GridPane.setConstraints(etiquetaUsuario, 0, 1);

        campoUsuario = new TextField();
        campoUsuario.setPromptText("Ingrese usuario");
        GridPane.setConstraints(campoUsuario, 1, 1);

        Label etiquetaContrasena = new Label("Contraseña:");
        GridPane.setConstraints(etiquetaContrasena, 0, 2);

        campoContrasena = new PasswordField();
        campoContrasena.setPromptText("Ingrese contraseña");
        GridPane.setConstraints(campoContrasena, 1, 2);

        // Botones
        HBox panelBotones = new HBox(10);
        panelBotones.setAlignment(Pos.CENTER);

        botonLogin = new Button("Iniciar Sesión");
        Button botonSalir = new Button("Salir");

        panelBotones.getChildren().addAll(botonLogin, botonSalir);
        GridPane.setConstraints(panelBotones, 0, 3, 2, 1);

        etiquetaMensaje = new Label();
        etiquetaMensaje.setStyle("-fx-text-fill: red;");
        GridPane.setConstraints(etiquetaMensaje, 0, 4, 2, 1);

        panelRaiz.getChildren().addAll(
                etiquetaTitulo, etiquetaUsuario, campoUsuario,
                etiquetaContrasena, campoContrasena, panelBotones, etiquetaMensaje
        );

        botonSalir.setOnAction(evento -> {
            DatabaseConnection.closeConnection();
            System.exit(0);
        });
    }

    private void configurarEventos() {
        botonLogin.setOnAction(evento -> {
            realizarLogin();
        });

        campoUsuario.setOnAction(evento -> realizarLogin());
        campoContrasena.setOnAction(evento -> realizarLogin());
    }

    private void realizarLogin() {
        String usuario = campoUsuario.getText().trim();
        String contrasena = campoContrasena.getText().trim();

        if (usuario.isEmpty() || contrasena.isEmpty()) {
            mostrarMensaje("Por favor, complete todos los campos", true);
            return;
        }

        Empleado empleado = controlador.autenticar(usuario, contrasena);
        if (empleado != null) {
            mostrarMensaje("Login exitoso", false);
            abrirVentanaPrincipal(empleado);
        } else {
            mostrarMensaje("Usuario o contraseña incorrectos", true);
            campoContrasena.clear();
        }
    }

    private void abrirVentanaPrincipal(Empleado empleado) {
        Stage ventanaActual = (Stage) panelRaiz.getScene().getWindow();
        ventanaActual.close();

        Stage ventanaPrincipal = new Stage();
        MainView vistaPrincipal = new MainView(empleado);

        Scene escena = vistaPrincipal.crearEscena();
        ventanaPrincipal.setScene(escena);
        ventanaPrincipal.setTitle("Sistema de Agencia de Autos");
        ventanaPrincipal.show();
    }

    private void mostrarLogin() {
        Stage ventanaLogin = new Stage();
        ventanaLogin.setTitle("Agencia de Autos - Login");

        campoUsuario.clear();
        campoContrasena.clear();
        etiquetaMensaje.setText("");

        ventanaLogin.setScene(new Scene(panelRaiz, 600, 400));
        ventanaLogin.show();
    }

    private void mostrarMensaje(String mensaje, boolean error) {
        etiquetaMensaje.setText(mensaje);
        if (error) {
            etiquetaMensaje.setStyle("-fx-text-fill: red;");
        } else {
            etiquetaMensaje.setStyle("-fx-text-fill: green;");
        }
    }



    public GridPane getRoot() {
        return panelRaiz;
    }
}