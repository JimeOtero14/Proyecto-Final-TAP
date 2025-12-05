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

        // Barra superior
        HBox barraSuperior = new HBox(10);
        barraSuperior.setPadding(new Insets(10));
        barraSuperior.setAlignment(Pos.CENTER_LEFT);

        Label titulo = new Label("Sistema de Agencia de Autos");

        Region espaciador = new Region();
        HBox.setHgrow(espaciador, Priority.ALWAYS);

        etiquetaUsuario = new Label("Usuario: " + empleadoActual.getNombreCompleto());

        Button botonCerrarSesion = new Button("Cerrar Sesión");
        botonCerrarSesion.setOnAction(e -> cerrarSesion());

        barraSuperior.getChildren().addAll(titulo, espaciador, etiquetaUsuario, botonCerrarSesion);
        panelRaiz.setTop(barraSuperior);

        // Menú lateral
        VBox menuLateral = crearMenuLateral();
        panelRaiz.setLeft(menuLateral);

        // Panel de contenido principal
        panelContenido = new VBox(10);
        panelContenido.setPadding(new Insets(20));
        panelContenido.setAlignment(Pos.TOP_LEFT);

        ScrollPane scrollPane = new ScrollPane(panelContenido);
        scrollPane.setFitToWidth(true);
        panelRaiz.setCenter(scrollPane);
    }

    private VBox crearMenuLateral() {
        VBox menu = new VBox(10);
        menu.setPadding(new Insets(20));
        menu.setPrefWidth(200);

        Label tituloMenu = new Label("MENÚ PRINCIPAL");

        Separator separador = new Separator();

        // Botones del menú
        Button btnInicio = new Button("Inicio");
        Button btnVehiculos = new Button("Vehículos");
        Button btnClientes = new Button("Clientes");
        Button btnVentas = new Button("Ventas");
        Button btnEmpleados = new Button("Empleados");
        Button btnReportes = new Button("Reportes");
        Button btnConfiguracion = new Button("Configuración");

        // Acciones de los botones
        btnInicio.setOnAction(e -> mostrarPanelBienvenida());
        btnVehiculos.setOnAction(e -> mostrarPanelVehiculos());
        btnClientes.setOnAction(e -> mostrarPanelClientes());
        btnVentas.setOnAction(e -> mostrarPanelVentas());
        btnEmpleados.setOnAction(e -> mostrarPanelEmpleados());
        btnReportes.setOnAction(e -> mostrarPanelReportes());
        btnConfiguracion.setOnAction(e -> mostrarPanelConfiguracion());

        // Añadir al menú
        menu.getChildren().addAll(
                tituloMenu,
                separador,
                btnInicio,
                btnVehiculos,
                btnClientes,
                btnVentas,
                btnEmpleados,
                btnReportes,
                btnConfiguracion
        );

        return menu;
    }

    private void mostrarPanelBienvenida() {
        panelContenido.getChildren().clear();

        Label titulo = new Label("Bienvenido al Sistema");
        titulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label mensaje = new Label("Seleccione una opción del menú lateral para comenzar.");
        mensaje.setStyle("-fx-font-size: 14px;");

        Label infoUsuario = new Label("Usuario actual: " + empleadoActual.getNombreCompleto());
        Label infoPuesto = new Label("Puesto: " + empleadoActual.getPuesto());

        panelContenido.getChildren().addAll(titulo, mensaje, infoUsuario, infoPuesto);
    }

    private void mostrarPanelVehiculos() {
        panelContenido.getChildren().clear();

        Label titulo = new Label("Gestión de Vehículos");
        titulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        // Submenú para vehículos
        HBox subMenu = new HBox(10);
        Button btnListar = new Button("Listar Vehículos");
        Button btnNuevo = new Button("Nuevo Vehículo");
        Button btnBuscar = new Button("Buscar Vehículo");

        subMenu.getChildren().addAll(btnListar, btnNuevo, btnBuscar);

        Label contenido = new Label("Aquí se mostrará la gestión de vehículos.");

        panelContenido.getChildren().addAll(titulo, subMenu, contenido);
    }

    private void mostrarPanelClientes() {
        panelContenido.getChildren().clear();

        Label titulo = new Label("Gestión de Clientes");
        titulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        // Submenú para clientes
        HBox subMenu = new HBox(10);
        Button btnListar = new Button("Listar Clientes");
        Button btnNuevo = new Button("Nuevo Cliente");
        Button btnBuscar = new Button("Buscar Cliente");

        subMenu.getChildren().addAll(btnListar, btnNuevo, btnBuscar);

        Label contenido = new Label("Aquí se mostrará la gestión de clientes.");

        panelContenido.getChildren().addAll(titulo, subMenu, contenido);
    }

    private void mostrarPanelVentas() {
        panelContenido.getChildren().clear();

        Label titulo = new Label("Registro de Ventas");
        titulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        // Submenú para ventas
        HBox subMenu = new HBox(10);
        Button btnNueva = new Button("Nueva Venta");
        Button btnHistorial = new Button("Historial");
        Button btnReporte = new Button("Reporte Ventas");

        subMenu.getChildren().addAll(btnNueva, btnHistorial, btnReporte);

        Label contenido = new Label("Aquí se mostrará el registro de ventas.");

        panelContenido.getChildren().addAll(titulo, subMenu, contenido);
    }

    private void mostrarPanelEmpleados() {
        panelContenido.getChildren().clear();

        Label titulo = new Label("Gestión de Empleados");
        titulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        // Submenú para empleados
        HBox subMenu = new HBox(10);
        Button btnListar = new Button("Listar Empleados");
        Button btnNuevo = new Button("Nuevo Empleado");
        Button btnPermisos = new Button("Permisos");

        subMenu.getChildren().addAll(btnListar, btnNuevo, btnPermisos);

        Label contenido = new Label("Aquí se mostrará la gestión de empleados.");

        panelContenido.getChildren().addAll(titulo, subMenu, contenido);
    }

    private void mostrarPanelReportes() {
        panelContenido.getChildren().clear();

        Label titulo = new Label("Reportes y Estadísticas");
        titulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        // Submenú para reportes
        VBox subMenu = new VBox(10);
        Button btnVentas = new Button("Reporte de Ventas");
        Button btnInventario = new Button("Reporte de Inventario");
        Button btnClientes = new Button("Reporte de Clientes");
        Button btnEmpleados = new Button("Reporte de Empleados");

        subMenu.getChildren().addAll(btnVentas, btnInventario, btnClientes, btnEmpleados);

        panelContenido.getChildren().addAll(titulo, subMenu);
    }

    private void mostrarPanelConfiguracion() {
        panelContenido.getChildren().clear();

        Label titulo = new Label("Configuración del Sistema");
        titulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        // Opciones de configuración
        VBox opciones = new VBox(10);

        CheckBox chkNotificaciones = new CheckBox("Activar notificaciones");
        CheckBox chkAutoguardado = new CheckBox("Autoguardado cada 5 minutos");

        Label lblTema = new Label("Tema de la interfaz:");
        ComboBox<String> comboTema = new ComboBox<>();
        comboTema.getItems().addAll("Claro", "Oscuro", "Automático");
        comboTema.setValue("Claro");

        Button btnGuardarConfig = new Button("Guardar Configuración");

        opciones.getChildren().addAll(
                chkNotificaciones,
                chkAutoguardado,
                lblTema,
                comboTema,
                btnGuardarConfig
        );

        panelContenido.getChildren().addAll(titulo, opciones);
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