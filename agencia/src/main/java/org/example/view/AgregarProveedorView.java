package org.example.view;

import org.example.controller.ProveedorController;
import org.example.model.Empleado;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class AgregarProveedorView {
    private Stage stage;
    private ProveedorController controller;
    private GridPane panelRaiz;

    public AgregarProveedorView(Stage stage, Empleado gerente) {
        this.stage = stage;
        this.controller = new ProveedorController(gerente);
        crearVista();
    }

    private void crearVista() {
        panelRaiz = new GridPane();
        panelRaiz.setPadding(new Insets(20));
        panelRaiz.setHgap(15);
        panelRaiz.setVgap(15);
        panelRaiz.setAlignment(Pos.CENTER);

        Label titulo = new Label("AGREGAR NUEVO PROVEEDOR");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        GridPane.setConstraints(titulo, 0, 0, 2, 1);

        int fila = 1;

        TextField campoId = crearCampoTexto("ID Proveedor*:");
        agregarFila("ID Proveedor*:", campoId, 0, fila++);

        TextField campoNombre = crearCampoTexto("Nombre*:");
        agregarFila("Nombre*:", campoNombre, 0, fila++);

        TextField campoTelefono = crearCampoTexto("Teléfono:");
        agregarFila("Teléfono:", campoTelefono, 0, fila++);

        TextField campoDireccion = crearCampoTexto("Dirección:");
        campoDireccion.setPrefWidth(300);
        agregarFila("Dirección:", campoDireccion, 0, fila++);

        TextField campoRfc = crearCampoTexto("RFC:");
        agregarFila("RFC:", campoRfc, 0, fila++);

        HBox panelBotones = new HBox(20);
        panelBotones.setAlignment(Pos.CENTER);
        panelBotones.setPadding(new Insets(20, 0, 0, 0));

        Button botonRegistrar = new Button("Registrar Proveedor");
        botonRegistrar.setStyle("-fx-font-weight: bold; -fx-padding: 10 25;");
        botonRegistrar.setOnAction(e -> procesarRegistro(campoId, campoNombre, campoTelefono, campoDireccion, campoRfc));

        Button botonCancelar = new Button("Cancelar");
        botonCancelar.setStyle("-fx-padding: 10 25;");
        botonCancelar.setOnAction(e -> controller.volverAGerenteView(stage));

        panelBotones.getChildren().addAll(botonRegistrar, botonCancelar);
        GridPane.setConstraints(panelBotones, 0, fila, 2, 1);

        panelRaiz.getChildren().addAll(titulo, panelBotones);

        Scene scene = new Scene(panelRaiz, 500, 400);
        stage.setScene(scene);
        stage.setTitle("Agregar Proveedor");
        stage.setResizable(false);

        try {
            scene.getStylesheets().add(getClass().getResource("/styles/estilos.css").toExternalForm());
        } catch (Exception e) {
            System.err.println("No se pudo cargar CSS: " + e.getMessage());
        }
    }

    private TextField crearCampoTexto(String prompt) {
        TextField campo = new TextField();
        campo.setPromptText(prompt);
        campo.setPrefWidth(250);
        return campo;
    }

    private void agregarFila(String etiqueta, Control control, int columna, int fila) {
        Label label = new Label(etiqueta);
        label.setStyle("-fx-font-weight: bold;");
        GridPane.setConstraints(label, columna, fila);
        GridPane.setConstraints(control, columna + 1, fila);
        panelRaiz.getChildren().addAll(label, control);
    }

    private void procesarRegistro(TextField campoId, TextField campoNombre, TextField campoTelefono, TextField campoDireccion, TextField campoRfc) {
        try {
            if (campoId.getText().trim().isEmpty()) {
                mostrarError("ID Proveedor es obligatorio");
                campoId.requestFocus();
                return;
            }

            if (campoNombre.getText().trim().isEmpty()) {
                mostrarError("Nombre es obligatorio");
                campoNombre.requestFocus();
                return;
            }

            boolean exito = controller.registrarProveedor(
                    campoId.getText().trim(),
                    campoNombre.getText().trim(),
                    campoTelefono.getText().trim(),
                    campoDireccion.getText().trim(),
                    campoRfc.getText().trim()
            );

            if (exito) {
                controller.volverAGerenteView(stage);
            }

        } catch (Exception e) {
            mostrarError("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error de Validación");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.initOwner(stage);
        alert.showAndWait();
    }

    public GridPane getRoot() {
        return panelRaiz;
    }
}