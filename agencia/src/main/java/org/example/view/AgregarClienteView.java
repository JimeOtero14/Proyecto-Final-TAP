package org.example.view;

import org.example.controller.ClienteController;
import org.example.model.Empleado;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class AgregarClienteView {
    private Stage stage;
    private ClienteController controller;
    private GridPane panelRaiz;

    public AgregarClienteView(Stage stage, Empleado gerente) {
        this.stage = stage;
        this.controller = new ClienteController();
        crearVista();
    }

    private void crearVista() {
        panelRaiz = new GridPane();
        panelRaiz.setPadding(new Insets(20));
        panelRaiz.setHgap(15);
        panelRaiz.setVgap(15);
        panelRaiz.setAlignment(Pos.CENTER);

        Label titulo = new Label("AGREGAR NUEVO CLIENTE");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        GridPane.setConstraints(titulo, 0, 0, 2, 1);

        int fila = 1;

        TextField campoId = crearCampoTexto("ID Cliente*:");
        agregarFila("ID Cliente*:", campoId, 0, fila++);

        TextField campoNombre = crearCampoTexto("Nombre*:");
        agregarFila("Nombre*:", campoNombre, 0, fila++);

        TextField campoApellidoPaterno = crearCampoTexto("Apellido Paterno*:");
        agregarFila("Apellido Paterno*:", campoApellidoPaterno, 0, fila++);

        TextField campoApellidoMaterno = crearCampoTexto("Apellido Materno:");
        agregarFila("Apellido Materno:", campoApellidoMaterno, 0, fila++);

        TextField campoDireccion = crearCampoTexto("Dirección:");
        campoDireccion.setPrefWidth(300);
        agregarFila("Dirección:", campoDireccion, 0, fila++);

        TextField campoTelefono = crearCampoTexto("Teléfono:");
        campoTelefono.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("\\d*")) {
                campoTelefono.setText(oldVal);
            }
        });
        agregarFila("Teléfono:", campoTelefono, 0, fila++);

        HBox panelBotones = new HBox(20);
        panelBotones.setAlignment(Pos.CENTER);
        panelBotones.setPadding(new Insets(20, 0, 0, 0));

        Button botonRegistrar = new Button("Registrar Cliente");
        botonRegistrar.setStyle("-fx-font-weight: bold; -fx-padding: 10 25; -fx-background-color: #28a745; -fx-text-fill: white;");
        botonRegistrar.setOnAction(e -> procesarRegistro(campoId, campoNombre, campoApellidoPaterno,
                campoApellidoMaterno, campoDireccion, campoTelefono));

        panelBotones.getChildren().addAll(botonRegistrar);
        GridPane.setConstraints(panelBotones, 0, fila, 2, 1);

        panelRaiz.getChildren().addAll(titulo, panelBotones);

        Scene scene = new Scene(panelRaiz, 500, 450);
        stage.setScene(scene);
        stage.setTitle("Agregar Cliente");
        stage.setResizable(false);
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

    private void procesarRegistro(TextField campoId, TextField campoNombre, TextField campoApellidoPaterno,
                                  TextField campoApellidoMaterno, TextField campoDireccion, TextField campoTelefono) {
        try {
            if (campoId.getText().trim().isEmpty()) {
                mostrarError("ID Cliente es obligatorio");
                campoId.requestFocus();
                return;
            }

            if (campoNombre.getText().trim().isEmpty()) {
                mostrarError("Nombre es obligatorio");
                campoNombre.requestFocus();
                return;
            }

            if (campoApellidoPaterno.getText().trim().isEmpty()) {
                mostrarError("Apellido Paterno es obligatorio");
                campoApellidoPaterno.requestFocus();
                return;
            }

            boolean exito = controller.registrarCliente(
                    campoId.getText().trim(),
                    campoNombre.getText().trim(),
                    campoApellidoPaterno.getText().trim(),
                    campoApellidoMaterno.getText().trim(),
                    campoDireccion.getText().trim(),
                    campoTelefono.getText().trim()
            );

            if (exito) {
                stage.close();
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