package org.example.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.example.database.DatabaseConnection;
import org.example.model.Empleado;

public class MainView {
    private BorderPane panelRaiz;
    private Empleado empleadoActual;
    private Label etiquetaUsuario;
    private VBox panelContenido;

    public MainView(Empleado empleado) {
        this.empleadoActual = empleado;
        crearVista();
        mostrarPanelBienvenida();
    }

    private void crearVista() {
        panelRaiz = new BorderPane();
        panelRaiz.setPadding(new Insets(10));

        HBox barraSuperior = new HBox(10);
        barraSuperior.setPadding(new Insets(10));
        barraSuperior.setAlignment(Pos.CENTER_LEFT);

        Label titulo = new Label("Sistema de Agencia de Autos");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Region espaciador = new Region();
        HBox.setHgrow(espaciador, Priority.ALWAYS);

        etiquetaUsuario = new Label("Usuario: " + empleadoActual.getNombreCompleto());

        Button botonCerrarSesion = new Button("Cerrar Sesión");
        botonCerrarSesion.setOnAction(e -> cerrarSesion());

        barraSuperior.getChildren().addAll(titulo, espaciador, etiquetaUsuario, botonCerrarSesion);
        panelRaiz.setTop(barraSuperior);

        VBox menuLateral = crearMenuLateral();
        panelRaiz.setLeft(menuLateral);

        panelContenido = new VBox();
        panelContenido.setPadding(new Insets(20));

        ScrollPane scrollPane = new ScrollPane(panelContenido);
        scrollPane.setFitToWidth(true);
        panelRaiz.setCenter(scrollPane);
    }

    private VBox crearMenuLateral() {
        VBox menu = new VBox(10);
        menu.setPadding(new Insets(20));
        menu.setPrefWidth(200);

        Label tituloMenu = new Label("MENÚ PRINCIPAL");
        tituloMenu.setStyle("-fx-font-weight: bold;");

        Separator separador = new Separator();

        Button btnRegistrarVehiculo = new Button("Registrar Vehículo");
        Button btnConsultarInventario = new Button("Consultar Inventario");
        Button btnGestionarCliente = new Button("Gestionar Cliente");
        Button btnProcesarVenta = new Button("Procesar Venta");
        Button btnConfigurarPrecios = new Button("Configurar Precios");
        Button btnGestionUsuarios = new Button("Gestión de Usuarios");

        btnRegistrarVehiculo.setOnAction(e -> mostrarRegistrarVehiculo());
        btnConsultarInventario.setOnAction(e -> mostrarConsultarInventario());
        btnGestionarCliente.setOnAction(e -> mostrarGestionarCliente());
        btnProcesarVenta.setOnAction(e -> mostrarProcesarVenta());
        btnConfigurarPrecios.setOnAction(e -> mostrarConfigurarPrecios());
        btnGestionUsuarios.setOnAction(e -> mostrarGestionUsuarios());

        btnRegistrarVehiculo.setMaxWidth(Double.MAX_VALUE);
        btnConsultarInventario.setMaxWidth(Double.MAX_VALUE);
        btnGestionarCliente.setMaxWidth(Double.MAX_VALUE);
        btnProcesarVenta.setMaxWidth(Double.MAX_VALUE);
        btnConfigurarPrecios.setMaxWidth(Double.MAX_VALUE);
        btnGestionUsuarios.setMaxWidth(Double.MAX_VALUE);

        menu.getChildren().addAll(
                tituloMenu,
                separador,
                btnRegistrarVehiculo,
                btnConsultarInventario,
                btnGestionarCliente,
                btnProcesarVenta,
                btnConfigurarPrecios,
                btnGestionUsuarios
        );

        return menu;
    }

    private void mostrarPanelBienvenida() {
        panelContenido.getChildren().clear();

        Label titulo = new Label("Bienvenido al Sistema");
        titulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label mensaje = new Label("Seleccione una opción del menú.");

        panelContenido.getChildren().addAll(titulo, mensaje);
    }

    private void mostrarRegistrarVehiculo() {
        panelContenido.getChildren().clear();
    }

    private void mostrarConsultarInventario() {
        panelContenido.getChildren().clear();
    }

    private void mostrarGestionarCliente() {
        panelContenido.getChildren().clear();
    }

    private void mostrarProcesarVenta() {
        panelContenido.getChildren().clear();
    }

    private void mostrarConfigurarPrecios() {
        panelContenido.getChildren().clear();
    }

    private void mostrarGestionUsuarios() {
        panelContenido.getChildren().clear();
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
        return new Scene(panelRaiz, 900, 600);
    }
}