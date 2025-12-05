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
    }

    private void crearVista() {
        panelRaiz = new BorderPane();
        panelRaiz.setPadding(new Insets(10));

        panelRaiz.setTop(barraSuperior);

        VBox menuLateral = crearMenuLateral();
        panelRaiz.setLeft(menuLateral);

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

        Separator separador = new Separator();


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
        );

        return menu;
    }

    private void mostrarPanelBienvenida() {
        panelContenido.getChildren().clear();

        titulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");



    }

    private void mostrarPanelVehiculos() {
        panelContenido.getChildren().clear();
        Label titulo = new Label("Gestión de Vehículos");
        titulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");


    }

    private void mostrarPanelClientes() {
        panelContenido.getChildren().clear();
        Label titulo = new Label("Gestión de Clientes");
        titulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");


    }

    private void mostrarPanelVentas() {
        panelContenido.getChildren().clear();
        Label titulo = new Label("Registro de Ventas");
        titulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");


    }

    private void mostrarPanelEmpleados() {
        panelContenido.getChildren().clear();
        Label titulo = new Label("Gestión de Empleados");
        titulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");


    }

    private void mostrarPanelReportes() {
        panelContenido.getChildren().clear();
        Label titulo = new Label("Reportes y Estadísticas");
        titulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");


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
    }
}