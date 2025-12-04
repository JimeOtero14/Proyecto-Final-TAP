package org.example.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.example.database.DatabaseConnection;
import org.example.model.Empleado;
//lol
public class MainView {
    private BorderPane panelRaiz;
    private Empleado empleadoActual;
    private Label etiquetaUsuario;
    private VBox panelContenido;

    public MainView(Empleado empleado) {
        this.empleadoActual = empleado;
        crearVista();
    }

    private void crearVista() {
        panelRaiz = new BorderPane();
        panelRaiz.setPadding(new Insets(10));

        HBox barraSuperior = crearBarraSuperior();
        panelRaiz.setTop(barraSuperior);

        VBox menuLateral = crearMenuLateral();
        panelRaiz.setLeft(menuLateral);

        panelContenido = new VBox(20);
        panelContenido.setPadding(new Insets(20));
        panelContenido.setAlignment(Pos.TOP_CENTER);

        mostrarPanelBienvenida();

        ScrollPane scrollPane = new ScrollPane(panelContenido);
        scrollPane.setFitToWidth(true);
        panelRaiz.setCenter(scrollPane);
    }

    private HBox crearBarraSuperior() {
        HBox barra = new HBox(20);
        barra.setPadding(new Insets(10));
        barra.setAlignment(Pos.CENTER_LEFT);
        barra.setStyle("-fx-background-color: #2c3e50; -fx-text-fill: white;");

        Label titulo = new Label("🚗 Sistema de Agencia de Autos");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");

        Region espaciador = new Region();
        HBox.setHgrow(espaciador, Priority.ALWAYS);

        etiquetaUsuario = new Label(empleadoActual.getNombreCompleto() + " - " + empleadoActual.getPuesto());
        etiquetaUsuario.setStyle("-fx-text-fill: white; -fx-font-size: 12px;");

        Button botonCerrarSesion = new Button("Cerrar Sesión");
        botonCerrarSesion.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
        botonCerrarSesion.setOnAction(e -> cerrarSesion());

        barra.getChildren().addAll(titulo, espaciador, etiquetaUsuario, botonCerrarSesion);
        return barra;
    }

    private VBox crearMenuLateral() {
        VBox menu = new VBox(10);
        menu.setPadding(new Insets(20));
        menu.setPrefWidth(200);
        menu.setStyle("-fx-background-color: #34495e;");

        Label tituloMenu = new Label("MENÚ PRINCIPAL");
        tituloMenu.setStyle("-fx-font-weight: bold; -fx-text-fill: white; -fx-font-size: 14px;");

        Separator separador = new Separator();

        Button btnInicio = crearBotonMenu("Inicio");
        Button btnVehiculos = crearBotonMenu("Vehículos");
        Button btnClientes = crearBotonMenu("Clientes");
        Button btnVentas = crearBotonMenu("Ventas");
        Button btnEmpleados = crearBotonMenu("Empleados");
        Button btnReportes = crearBotonMenu("Reportes");

        btnInicio.setOnAction(e -> mostrarPanelBienvenida());
        btnVehiculos.setOnAction(e -> mostrarPanelVehiculos());
        btnClientes.setOnAction(e -> mostrarPanelClientes());
        btnVentas.setOnAction(e -> mostrarPanelVentas());
        btnEmpleados.setOnAction(e -> mostrarPanelEmpleados());
        btnReportes.setOnAction(e -> mostrarPanelReportes());

        menu.getChildren().addAll(
                tituloMenu,
                separador,
                btnInicio,
                btnVehiculos,
                btnClientes,
                btnVentas,
                btnEmpleados,
                btnReportes
        );

        return menu;
    }

    private Button crearBotonMenu(String texto) {
        Button boton = new Button(texto);
        boton.setMaxWidth(Double.MAX_VALUE);
        boton.setAlignment(Pos.CENTER_LEFT);
        boton.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 13px; " +
                        "-fx-padding: 10px;"
        );

        boton.setOnMouseEntered(e ->
                boton.setStyle(
                        "-fx-background-color: #2c3e50; " +
                                "-fx-text-fill: white; " +
                                "-fx-font-size: 13px; " +
                                "-fx-padding: 10px;"
                )
        );

        boton.setOnMouseExited(e ->
                boton.setStyle(
                        "-fx-background-color: transparent; " +
                                "-fx-text-fill: white; " +
                                "-fx-font-size: 13px; " +
                                "-fx-padding: 10px;"
                )
        );

        return boton;
    }

    private void mostrarPanelBienvenida() {
        panelContenido.getChildren().clear();

        Label titulo = new Label("Bienvenido, " + empleadoActual.getNombre());
        titulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label subtitulo = new Label("Panel de Control - " + empleadoActual.getPuesto());
        subtitulo.setStyle("-fx-font-size: 16px; -fx-text-fill: #7f8c8d;");

        // Panel de estadísticas rápidas
        HBox panelEstadisticas = new HBox(20);
        panelEstadisticas.setAlignment(Pos.CENTER);
        panelEstadisticas.setPadding(new Insets(30, 0, 0, 0));

        VBox estatVehiculos = crearTarjetaEstadistica("Vehículos", "0", "#3498db");
        VBox estatClientes = crearTarjetaEstadistica("Clientes", "0", "#2ecc71");
        VBox estatVentas = crearTarjetaEstadistica( "Ventas Hoy", "$0", "#e74c3c");
        VBox estatEmpleados = crearTarjetaEstadistica("Empleados", "0", "#f39c12");

        panelEstadisticas.getChildren().addAll(
                estatVehiculos,
                estatClientes,
                estatVentas,
                estatEmpleados
        );

        panelContenido.getChildren().addAll(titulo, subtitulo, panelEstadisticas);
    }

    private VBox crearTarjetaEstadistica(String titulo, String valor, String color) {
        VBox tarjeta = new VBox(10);
        tarjeta.setAlignment(Pos.CENTER);
        tarjeta.setPadding(new Insets(20));
        tarjeta.setPrefSize(150, 120);
        tarjeta.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: " + color + "; " +
                        "-fx-border-width: 2px; " +
                        "-fx-background-radius: 10px; " +
                        "-fx-border-radius: 10px;"
        );

        Label lblIcono = new Label(icono);
        lblIcono.setStyle("-fx-font-size: 30px;");

        Label lblTitulo = new Label(titulo);
        lblTitulo.setStyle("-fx-font-size: 12px; -fx-text-fill: #7f8c8d;");

        Label lblValor = new Label(valor);
        lblValor.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: " + color + ";");

        tarjeta.getChildren().addAll(lblIcono, lblTitulo, lblValor);
        return tarjeta;
    }

    private void mostrarPanelVehiculos() {
        panelContenido.getChildren().clear();
        Label titulo = new Label("Gestión de Vehículos");
        titulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        Label mensaje = new Label("Módulo de vehículos en construcción...");
        mensaje.setStyle("-fx-font-size: 14px; -fx-text-fill: #7f8c8d;");

        panelContenido.getChildren().addAll(titulo, mensaje);
    }

    private void mostrarPanelClientes() {
        panelContenido.getChildren().clear();
        Label titulo = new Label("Gestión de Clientes");
        titulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        Label mensaje = new Label("Módulo de clientes en construcción...");
        mensaje.setStyle("-fx-font-size: 14px; -fx-text-fill: #7f8c8d;");

        panelContenido.getChildren().addAll(titulo, mensaje);
    }

    private void mostrarPanelVentas() {
        panelContenido.getChildren().clear();
        Label titulo = new Label("Registro de Ventas");
        titulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        Label mensaje = new Label("Módulo de ventas en construcción...");
        mensaje.setStyle("-fx-font-size: 14px; -fx-text-fill: #7f8c8d;");

        panelContenido.getChildren().addAll(titulo, mensaje);
    }

    private void mostrarPanelEmpleados() {
        panelContenido.getChildren().clear();
        Label titulo = new Label("Gestión de Empleados");
        titulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        Label mensaje = new Label("Módulo de empleados en construcción...");
        mensaje.setStyle("-fx-font-size: 14px; -fx-text-fill: #7f8c8d;");

        panelContenido.getChildren().addAll(titulo, mensaje);
    }

    private void mostrarPanelReportes() {
        panelContenido.getChildren().clear();
        Label titulo = new Label("Reportes y Estadísticas");
        titulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        Label mensaje = new Label("Módulo de reportes en construcción...");
        mensaje.setStyle("-fx-font-size: 14px; -fx-text-fill: #7f8c8d;");

        panelContenido.getChildren().addAll(titulo, mensaje);
    }

    private void cerrarSesion() {
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Cerrar Sesión");
        confirmacion.setHeaderText("¿Está seguro que desea cerrar sesión?");
        confirmacion.setContentText("Presione OK para confirmar");

        confirmacion.showAndWait().ifPresent(respuesta -> {
            if (respuesta == ButtonType.OK) {
                Stage ventanaActual = (Stage) panelRaiz.getScene().getWindow();
                ventanaActual.close();

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

    public BorderPane getRoot() {
        return panelRaiz;
    }

    public Scene crearEscena() {
        return new Scene(panelRaiz, 1000, 700);
    }
}