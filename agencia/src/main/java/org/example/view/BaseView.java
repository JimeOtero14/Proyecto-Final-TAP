package org.example.view;

import org.example.controller.LoginController;
import org.example.model.Empleado;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public abstract class BaseView {
    protected BorderPane panelRaiz;
    protected Empleado empleadoActual;
    protected LoginController controlador;
    protected VBox panelBotones;

    public BaseView(LoginController controlador, Empleado empleado) {
        this.controlador = controlador;
        this.empleadoActual = empleado;
        crearVistaBase();
        aplicarEstilosBase();
        crearBotonesEspecificos();
        mostrarContenido();

    }

    protected void aplicarEstilosBase() {
        try {
            panelRaiz.getStylesheets().add(
                    getClass().getResource("/styles/estilos.css").toExternalForm()
            );
        } catch (Exception e) {
            System.err.println("Error cargando CSS: " + e.getMessage());
        }

        panelRaiz.getStyleClass().add("main-container");
    }

    protected void crearVistaBase() {
        panelRaiz = new BorderPane();
        panelRaiz.setPadding(new Insets(10));

        HBox barraSuperior = new HBox(10);
        barraSuperior.setPadding(new Insets(10));
        barraSuperior.setAlignment(Pos.CENTER_LEFT);
        barraSuperior.getStyleClass().add("top-bar");

        Label titulo = new Label("Sistema Agencia de Autos - " + empleadoActual.getPuesto());
        titulo.getStyleClass().add("top-bar-title");

        Region espaciador = new Region();
        HBox.setHgrow(espaciador, Priority.ALWAYS);

        Label etiquetaUsuario = new Label("Usuario: " + empleadoActual.getNombreCompleto());

        Button botonCerrarSesion = new Button("Cerrar Sesión");
        botonCerrarSesion.setOnAction(e -> {
            Stage stageActual = (Stage) ((Button) e.getSource()).getScene().getWindow();
            controlador.cerrarSesion(stageActual);
        });
        botonCerrarSesion.getStyleClass().add("logout-button");

        barraSuperior.getChildren().addAll(titulo, espaciador, etiquetaUsuario, botonCerrarSesion);
        panelRaiz.setTop(barraSuperior);

        panelBotones = new VBox(20);
        panelBotones.setPadding(new Insets(40));
        panelBotones.setAlignment(Pos.CENTER);

        javafx.scene.control.ScrollPane scrollPane = new javafx.scene.control.ScrollPane(panelBotones);
        scrollPane.setFitToWidth(true);
        panelRaiz.setCenter(scrollPane);
    }

    protected abstract void crearBotonesEspecificos();

    protected void mostrarContenido() {
        Label tituloBienvenida = new Label("Bienvenido, " + empleadoActual.getNombreCompleto());
        tituloBienvenida.getStyleClass().add("welcome-title");

        Label subtitulo = new Label("Seleccione una opción:");
        subtitulo.getStyleClass().add("welcome-subtitle");

        VBox contenido = new VBox(10);
        contenido.setAlignment(Pos.CENTER);
        contenido.getChildren().addAll(tituloBienvenida, subtitulo, panelBotones);

        panelRaiz.setCenter(contenido);
    }

    protected Button crearBotonGrande(String texto) {
        Button boton = new Button(texto);
        boton.setPrefSize(300, 60);

        return boton;
    }

    public BorderPane getRoot() {
        return panelRaiz;
    }
}
