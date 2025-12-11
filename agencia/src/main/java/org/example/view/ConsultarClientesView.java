package org.example.view;

import org.example.controller.ClienteController;
import org.example.model.Cliente;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;

public class ConsultarClientesView {
    private Stage stage;
    private ClienteController controller;
    private VBox panelRaiz;
    private TableView<Cliente> tablaClientes;
    private boolean esGerente;

    public ConsultarClientesView(Stage stage, boolean esGerente) {
        this.stage = stage;
        this.controller = new ClienteController();
        this.esGerente = esGerente;
        crearVista();
        cargarClientes();
    }

    private void crearVista() {
        panelRaiz = new VBox(20);
        panelRaiz.setPadding(new Insets(20));
        panelRaiz.setAlignment(Pos.TOP_CENTER);

        Label titulo = new Label(esGerente ? "GESTIÓN DE CLIENTES" : "CONSULTAR CLIENTES");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // Crear tabla
        tablaClientes = new TableView<>();
        tablaClientes.setPrefHeight(400);

        // Columnas de la tabla
        TableColumn<Cliente, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getIdCliente()));
        colId.setPrefWidth(80);

        TableColumn<Cliente, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNombre()));
        colNombre.setPrefWidth(100);

        TableColumn<Cliente, String> colApellidoPaterno = new TableColumn<>("Apellido Paterno");
        colApellidoPaterno.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getApellidoPaterno()));
        colApellidoPaterno.setPrefWidth(120);

        TableColumn<Cliente, String> colApellidoMaterno = new TableColumn<>("Apellido Materno");
        colApellidoMaterno.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getApellidoMaterno()));
        colApellidoMaterno.setPrefWidth(120);

        TableColumn<Cliente, String> colDireccion = new TableColumn<>("Dirección");
        colDireccion.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDireccion()));
        colDireccion.setPrefWidth(150);

        TableColumn<Cliente, String> colTelefono = new TableColumn<>("Teléfono");
        colTelefono.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTelefono()));
        colTelefono.setPrefWidth(100);

        tablaClientes.getColumns().addAll(colId, colNombre, colApellidoPaterno, colApellidoMaterno, colDireccion, colTelefono);

        // Panel de botones según el rol
        HBox panelBotones = new HBox(20);
        panelBotones.setAlignment(Pos.CENTER);
        panelBotones.setPadding(new Insets(10, 0, 0, 0));

        if (esGerente) {
            // Botones para Gerente
            Button btnAgregar = new Button("Agregar Cliente");
            btnAgregar.setStyle("-fx-font-weight: bold; -fx-padding: 10 20;");
            btnAgregar.setOnAction(e -> abrirAgregarCliente());

            Button btnEditar = new Button("Editar Cliente");
            btnEditar.setStyle("-fx-font-weight: bold; -fx-padding: 10 20;");
            btnEditar.setOnAction(e -> editarCliente());

            Button btnEliminar = new Button("Eliminar Cliente");
            btnEliminar.setStyle("-fx-font-weight: bold; -fx-padding: 10 20; -fx-background-color: #dc3545; -fx-text-fill: white;");
            btnEliminar.setOnAction(e -> eliminarCliente());

            Button btnActualizar = new Button("Actualizar Lista");
            btnActualizar.setStyle("-fx-padding: 10 20;");
            btnActualizar.setOnAction(e -> cargarClientes());

            panelBotones.getChildren().addAll(btnAgregar, btnEditar, btnEliminar, btnActualizar);
        } else {
            // Solo botón de actualizar para Vendedor
            Button btnActualizar = new Button("Actualizar Lista");
            btnActualizar.setStyle("-fx-padding: 10 20;");
            btnActualizar.setOnAction(e -> cargarClientes());

            panelBotones.getChildren().add(btnActualizar);
        }

        Button btnVolver = new Button("Volver");
        btnVolver.setStyle("-fx-padding: 10 20;");
        btnVolver.setOnAction(e -> stage.close());

        panelBotones.getChildren().add(btnVolver);

        panelRaiz.getChildren().addAll(titulo, tablaClientes, panelBotones);

        Scene scene = new Scene(panelRaiz, 800, 600);
        stage.setScene(scene);
        stage.setTitle(esGerente ? "Gestión de Clientes - Gerente" : "Consulta de Clientes - Vendedor");
        stage.setResizable(false);
    }

    private void cargarClientes() {
        List<Cliente> clientes = controller.listarClientes();
        ObservableList<Cliente> listaClientes = FXCollections.observableArrayList(clientes);
        tablaClientes.setItems(listaClientes);
    }

    private void abrirAgregarCliente() {
        Stage nuevaVentana = new Stage();
        AgregarClienteView vistaAgregar = new AgregarClienteView(nuevaVentana, null); // No necesita gerente para esto
        nuevaVentana.showAndWait();
        cargarClientes();
    }

    private void editarCliente() {
        Cliente clienteSeleccionado = tablaClientes.getSelectionModel().getSelectedItem();
        if (clienteSeleccionado == null) {
            mostrarError("Seleccione un cliente para editar");
            return;
        }

        // Aquí podrías implementar una ventana de edición similar a AgregarClienteView
        // Por ahora solo mostramos un mensaje
        mostrarMensaje("Editar cliente: " + clienteSeleccionado.getNombreCompleto());
    }

    private void eliminarCliente() {
        Cliente clienteSeleccionado = tablaClientes.getSelectionModel().getSelectedItem();
        if (clienteSeleccionado == null) {
            mostrarError("Seleccione un cliente para eliminar");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar Eliminación");
        confirmacion.setHeaderText("¿Está seguro de eliminar al cliente?");
        confirmacion.setContentText("Cliente: " + clienteSeleccionado.getNombreCompleto() +
                "\nID: " + clienteSeleccionado.getIdCliente());

        confirmacion.showAndWait().ifPresent(respuesta -> {
            if (respuesta == ButtonType.OK) {
                boolean exito = controller.eliminarCliente(clienteSeleccionado.getIdCliente());
                if (exito) {
                    mostrarMensaje("Cliente eliminado correctamente");
                    cargarClientes();
                } else {
                    mostrarError("No se pudo eliminar el cliente");
                }
            }
        });
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public VBox getRoot() {
        return panelRaiz;
    }
}