package org.example.view;

import org.example.controller.MantenimientoController;
import org.example.model.Empleado;
import org.example.model.Vehiculo;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class RegistrarMantenimientoView {
    private Stage stage;
    private MantenimientoController controller;
    private GridPane panelRaiz;
    private ComboBox<Vehiculo> comboVehiculos;
    private ComboBox<String> comboTipoMantenimiento;

    public RegistrarMantenimientoView(Stage stage, Empleado mecanico) {
        this.stage = stage;
        this.controller = new MantenimientoController(mecanico);
        crearVista();
        cargarVehiculos();
    }

    private void crearVista() {
        panelRaiz = new GridPane();
        panelRaiz.setPadding(new Insets(20));
        panelRaiz.setHgap(15);
        panelRaiz.setVgap(15);
        panelRaiz.setAlignment(Pos.CENTER);

        Label titulo = new Label("REGISTRAR MANTENIMIENTO");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        GridPane.setConstraints(titulo, 0, 0, 2, 1);

        int fila = 1;

        // ID Mantenimiento
        panelRaiz.add(new Label("ID Mantenimiento*:"), 0, fila);
        TextField campoIdMantenimiento = new TextField();
        campoIdMantenimiento.setPromptText("Ej: MANT001");
        campoIdMantenimiento.setPrefWidth(250);
        panelRaiz.add(campoIdMantenimiento, 1, fila);
        fila++;

        // Vehículo
        panelRaiz.add(new Label("Vehículo*:"), 0, fila);
        comboVehiculos = new ComboBox<>();
        comboVehiculos.setPromptText("Seleccione vehículo...");
        comboVehiculos.setPrefWidth(250);
        panelRaiz.add(comboVehiculos, 1, fila);
        fila++;

        // Fecha Mantenimiento
        panelRaiz.add(new Label("Fecha Mantenimiento:"), 0, fila);
        DatePicker selectorFecha = new DatePicker();
        selectorFecha.setValue(LocalDate.now());
        selectorFecha.setPrefWidth(250);
        panelRaiz.add(selectorFecha, 1, fila);
        fila++;

        // Kilometraje al Momento
        panelRaiz.add(new Label("Kilometraje Actual:"), 0, fila);
        TextField campoKilometraje = new TextField();
        campoKilometraje.setPromptText("0.00");
        campoKilometraje.setPrefWidth(250);
        campoKilometraje.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("\\d*(\\.\\d*)?")) {
                campoKilometraje.setText(oldVal);
            }
        });
        panelRaiz.add(campoKilometraje, 1, fila);
        fila++;

        // Tipo Mantenimiento
        panelRaiz.add(new Label("Tipo*:"), 0, fila);
        comboTipoMantenimiento = new ComboBox<>();
        comboTipoMantenimiento.getItems().addAll(
                "Preventivo",
                "Correctivo",
                "Cambio de Aceite",
                "Frenos",
                "Suspensión",
                "Motor",
                "Transmisión",
                "Eléctrico",
                "Afinación"
        );
        comboTipoMantenimiento.setPromptText("Seleccione tipo...");
        comboTipoMantenimiento.setPrefWidth(250);
        panelRaiz.add(comboTipoMantenimiento, 1, fila);
        fila++;

        // Próximo Mantenimiento
        panelRaiz.add(new Label("Próximo Mantenimiento:"), 0, fila);
        DatePicker selectorProximo = new DatePicker();
        selectorProximo.setPrefWidth(250);
        panelRaiz.add(selectorProximo, 1, fila);
        fila++;

        // Costo
        panelRaiz.add(new Label("Costo ($):"), 0, fila);
        TextField campoCosto = new TextField();
        campoCosto.setPromptText("0.00");
        campoCosto.setPrefWidth(250);
        campoCosto.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("\\d*(\\.\\d*)?")) {
                campoCosto.setText(oldVal);
            }
        });
        panelRaiz.add(campoCosto, 1, fila);
        fila++;

        // Estado
        panelRaiz.add(new Label("Estado:"), 0, fila);
        ComboBox<String> comboEstado = new ComboBox<>();
        comboEstado.getItems().addAll("Completado", "En Progreso", "Pendiente");
        comboEstado.setValue("Completado");
        comboEstado.setPrefWidth(250);
        panelRaiz.add(comboEstado, 1, fila);
        fila++;

        // Descripción
        panelRaiz.add(new Label("Descripción:"), 0, fila);
        TextArea areaDescripcion = new TextArea();
        areaDescripcion.setPrefRowCount(4);
        areaDescripcion.setPrefWidth(250);
        areaDescripcion.setWrapText(true);
        panelRaiz.add(areaDescripcion, 1, fila);
        fila++;

        // Panel de botones - SOLO UN BOTÓN
        HBox panelBotones = new HBox(20);
        panelBotones.setAlignment(Pos.CENTER);
        panelBotones.setPadding(new Insets(20, 0, 0, 0));

        Button btnRegistrar = new Button("Registrar Mantenimiento");
        btnRegistrar.setStyle("-fx-font-weight: bold; -fx-padding: 10 25; -fx-background-color: #28a745; -fx-text-fill: white;");
        btnRegistrar.setOnAction(e -> {
            if (procesarRegistro(
                    campoIdMantenimiento,
                    selectorFecha,
                    campoKilometraje,
                    comboTipoMantenimiento,
                    selectorProximo,
                    campoCosto,
                    comboEstado,
                    areaDescripcion
            )) {
                stage.close();
            }
        });

        panelBotones.getChildren().add(btnRegistrar);
        GridPane.setConstraints(panelBotones, 0, fila, 2, 1);

        panelRaiz.getChildren().addAll(titulo, panelBotones);

        Scene scene = new Scene(panelRaiz, 500, 650);
        stage.setScene(scene);
        stage.setTitle("Registrar Mantenimiento");
        stage.setResizable(false);
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

    private boolean procesarRegistro(
            TextField campoIdMantenimiento,
            DatePicker selectorFecha,
            TextField campoKilometraje,
            ComboBox<String> comboTipoMantenimiento,
            DatePicker selectorProximo,
            TextField campoCosto,
            ComboBox<String> comboEstado,
            TextArea areaDescripcion) {

        try {
            if (campoIdMantenimiento.getText().trim().isEmpty()) {
                mostrarError("ID Mantenimiento es obligatorio");
                campoIdMantenimiento.requestFocus();
                return false;
            }

            if (comboVehiculos.getValue() == null) {
                mostrarError("Seleccione un vehículo");
                return false;
            }

            if (comboTipoMantenimiento.getValue() == null) {
                mostrarError("Seleccione tipo de mantenimiento");
                return false;
            }

            BigDecimal kilometrajeMomento = campoKilometraje.getText().isEmpty() ?
                    BigDecimal.ZERO : new BigDecimal(campoKilometraje.getText());

            BigDecimal costo = campoCosto.getText().isEmpty() ?
                    BigDecimal.ZERO : new BigDecimal(campoCosto.getText());

            boolean exito = controller.registrarMantenimiento(
                    campoIdMantenimiento.getText().trim(),
                    comboVehiculos.getValue().getIdVehiculo(),
                    selectorFecha.getValue(),
                    comboTipoMantenimiento.getValue(),
                    areaDescripcion.getText().trim(),
                    costo,
                    comboEstado.getValue(),
                    kilometrajeMomento,
                    selectorProximo.getValue()
            );

            if (exito) {
                mostrarMensajeExito("Mantenimiento registrado correctamente");
                return true;
            } else {
                mostrarError("No se pudo registrar el mantenimiento");
                return false;
            }

        } catch (NumberFormatException e) {
            mostrarError("Formato numérico inválido. Use números con punto decimal");
            return false;
        } catch (Exception e) {
            mostrarError("Error: " + e.getMessage());
            e.printStackTrace();
            return false;
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

    private void mostrarMensajeExito(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.initOwner(stage);
        alert.showAndWait();
    }

    public GridPane getRoot() {
        return panelRaiz;
    }
}