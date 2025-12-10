package org.example.view;

import org.example.controller.DocumentoController;
import org.example.model.Empleado;
import org.example.model.Vehiculo;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.File;
import java.sql.Blob;
import java.util.List;

public class AgregarDocumentoView {
    private Stage stage;
    private DocumentoController controller;
    private GridPane panelRaiz;
    private File archivoSeleccionado;
    private ComboBox<Vehiculo> comboVehiculos;
    private Label etiquetaArchivo;

    public AgregarDocumentoView(Stage stage, Empleado gerente) {
        this.stage = stage;
        this.controller = new DocumentoController(gerente);
        crearVista();
    }

    private void crearVista() {
        panelRaiz = new GridPane();
        panelRaiz.setPadding(new Insets(20));
        panelRaiz.setHgap(15);
        panelRaiz.setVgap(15);
        panelRaiz.setAlignment(Pos.CENTER);

        Label titulo = new Label("AGREGAR DOCUMENTO");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        GridPane.setConstraints(titulo, 0, 0, 2, 1);

        int fila = 1;

        Label lblVehiculo = new Label("Vehículo*:");
        lblVehiculo.setStyle("-fx-font-weight: bold;");
        GridPane.setConstraints(lblVehiculo, 0, fila);

        comboVehiculos = new ComboBox<>();
        cargarVehiculos();
        comboVehiculos.setPrefWidth(250);
        GridPane.setConstraints(comboVehiculos, 1, fila);
        fila++;

        TextField campoIdArchivo = crearCampoTexto("ID Archivo*:");
        agregarFila("ID Archivo*:", campoIdArchivo, 0, fila++);

        TextField campoNombre = crearCampoTexto("Nombre*:");
        agregarFila("Nombre*:", campoNombre, 0, fila++);

        ComboBox<String> comboTipoDocumento = new ComboBox<>();
        comboTipoDocumento.getItems().addAll("Factura", "Tenencia", "Verificación", "Seguro", "Manual", "Otros");
        comboTipoDocumento.setPromptText("Seleccione tipo");
        comboTipoDocumento.setPrefWidth(250);
        agregarFila("Tipo Documento:", comboTipoDocumento, 0, fila++);

        Label lblArchivo = new Label("Archivo PDF*:");
        lblArchivo.setStyle("-fx-font-weight: bold;");
        GridPane.setConstraints(lblArchivo, 0, fila);

        HBox panelArchivo = new HBox(10);
        panelArchivo.setAlignment(Pos.CENTER_LEFT);

        Button btnSeleccionarArchivo = new Button("Seleccionar PDF");
        btnSeleccionarArchivo.setOnAction(e -> seleccionarArchivo());

        etiquetaArchivo = new Label("Sin archivo seleccionado");
        etiquetaArchivo.setStyle("-fx-text-fill: gray;");

        panelArchivo.getChildren().addAll(btnSeleccionarArchivo, etiquetaArchivo);
        GridPane.setConstraints(panelArchivo, 1, fila);
        fila++;

        HBox panelBotones = new HBox(20);
        panelBotones.setAlignment(Pos.CENTER);
        panelBotones.setPadding(new Insets(20, 0, 0, 0));

        Button botonRegistrar = new Button("Registrar Documento");
        botonRegistrar.setStyle("-fx-font-weight: bold; -fx-padding: 10 25;");
        botonRegistrar.setOnAction(e -> procesarRegistro(campoIdArchivo, campoNombre, comboTipoDocumento));

        Button botonCancelar = new Button("Cancelar");
        botonCancelar.setStyle("-fx-padding: 10 25;");
        botonCancelar.setOnAction(e -> controller.volverAGerenteView(stage));

        panelBotones.getChildren().addAll(botonRegistrar, botonCancelar);
        GridPane.setConstraints(panelBotones, 0, fila, 2, 1);

        panelRaiz.getChildren().addAll(titulo, lblVehiculo, comboVehiculos, lblArchivo, panelArchivo, panelBotones);

        Scene scene = new Scene(panelRaiz, 600, 450);
        stage.setScene(scene);
        stage.setTitle("Agregar Documento");
        stage.setResizable(false);

        try {
            scene.getStylesheets().add(getClass().getResource("/styles/estilos.css").toExternalForm());
        } catch (Exception e) {
            System.err.println("No se pudo cargar CSS: " + e.getMessage());
        }
    }

    private void cargarVehiculos() {
        List<Vehiculo> vehiculos = controller.obtenerVehiculos();
        ObservableList<Vehiculo> listaVehiculos = FXCollections.observableArrayList(vehiculos);
        comboVehiculos.setItems(listaVehiculos);

        comboVehiculos.setCellFactory(param -> new ListCell<Vehiculo>() {
            @Override
            protected void updateItem(Vehiculo vehiculo, boolean empty) {
                super.updateItem(vehiculo, empty);
                if (empty || vehiculo == null) {
                    setText(null);
                } else {
                    setText(vehiculo.getMarca() + " " + vehiculo.getModelo() + " " + vehiculo.getAño() + " - " + vehiculo.getIdVehiculo());
                }
            }
        });

        comboVehiculos.setButtonCell(new ListCell<Vehiculo>() {
            @Override
            protected void updateItem(Vehiculo vehiculo, boolean empty) {
                super.updateItem(vehiculo, empty);
                if (empty || vehiculo == null) {
                    setText("Seleccione un vehículo");
                } else {
                    setText(vehiculo.getMarca() + " " + vehiculo.getModelo() + " " + vehiculo.getAño());
                }
            }
        });
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

    private void seleccionarArchivo() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Documento PDF");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf"),
                new FileChooser.ExtensionFilter("Todos los archivos", "*.*")
        );

        Stage stageActual = (Stage) panelRaiz.getScene().getWindow();
        archivoSeleccionado = fileChooser.showOpenDialog(stageActual);

        if (archivoSeleccionado != null) {
            etiquetaArchivo.setText(archivoSeleccionado.getName());
            etiquetaArchivo.setStyle("-fx-text-fill: black;");
        }
    }

    private void procesarRegistro(TextField campoIdArchivo, TextField campoNombre, ComboBox<String> comboTipoDocumento) {
        try {
            if (comboVehiculos.getValue() == null) {
                mostrarError("Seleccione un vehículo");
                return;
            }

            if (campoIdArchivo.getText().trim().isEmpty()) {
                mostrarError("ID Archivo es obligatorio");
                campoIdArchivo.requestFocus();
                return;
            }

            if (campoNombre.getText().trim().isEmpty()) {
                mostrarError("Nombre es obligatorio");
                campoNombre.requestFocus();
                return;
            }

            if (archivoSeleccionado == null) {
                mostrarError("Seleccione un archivo PDF");
                return;
            }

            String idVehiculo = comboVehiculos.getValue().getIdVehiculo();
            String tipoDocumento = comboTipoDocumento.getValue() != null ? comboTipoDocumento.getValue() : "Otros";

            Blob archivoBlob = null;
            if (archivoSeleccionado != null) {
                try {
                    byte[] bytes = java.nio.file.Files.readAllBytes(archivoSeleccionado.toPath());
                    archivoBlob = new javax.sql.rowset.serial.SerialBlob(bytes);
                } catch (Exception e) {
                    mostrarError("Error al procesar el archivo: " + e.getMessage());
                    return;
                }
            }

            boolean exito = controller.registrarDocumento(
                    idVehiculo,
                    campoIdArchivo.getText().trim(),
                    campoNombre.getText().trim(),
                    archivoBlob,
                    tipoDocumento
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