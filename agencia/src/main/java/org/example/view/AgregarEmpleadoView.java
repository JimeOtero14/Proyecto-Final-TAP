package org.example.view;

import org.example.controller.EmpleadoController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.math.BigDecimal;
import java.time.LocalDate;

public class AgregarEmpleadoView {
    private Stage stage;
    private EmpleadoController controller;
    private GridPane panelRaiz;

    public AgregarEmpleadoView(Stage stage) {
        this.stage = stage;
        this.controller = new EmpleadoController();
        crearVista();
    }

    private void crearVista() {
        panelRaiz = new GridPane();
        panelRaiz.setPadding(new Insets(20));
        panelRaiz.setHgap(15);
        panelRaiz.setVgap(10);
        panelRaiz.setAlignment(Pos.CENTER);

        Label titulo = new Label("REGISTRAR NUEVO EMPLEADO");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        GridPane.setConstraints(titulo, 0, 0, 2, 1);

        int fila = 1;

        panelRaiz.add(new Label("ID Empleado*:"), 0, fila);
        TextField campoIdEmpleado = new TextField();
        campoIdEmpleado.setId("idEmpleado");
        campoIdEmpleado.setPrefWidth(250);
        panelRaiz.add(campoIdEmpleado, 1, fila);
        fila++;

        panelRaiz.add(new Label("Nombre*:"), 0, fila);
        TextField campoNombre = new TextField();
        campoNombre.setId("nombre");
        campoNombre.setPrefWidth(250);
        panelRaiz.add(campoNombre, 1, fila);
        fila++;

        panelRaiz.add(new Label("Apellido Paterno*:"), 0, fila);
        TextField campoApellidoPaterno = new TextField();
        campoApellidoPaterno.setId("apellidoPaterno");
        campoApellidoPaterno.setPrefWidth(250);
        panelRaiz.add(campoApellidoPaterno, 1, fila);
        fila++;

        panelRaiz.add(new Label("Apellido Materno:"), 0, fila);
        TextField campoApellidoMaterno = new TextField();
        campoApellidoMaterno.setId("apellidoMaterno");
        campoApellidoMaterno.setPrefWidth(250);
        panelRaiz.add(campoApellidoMaterno, 1, fila);
        fila++;

        panelRaiz.add(new Label("Puesto:"), 0, fila);
        ComboBox<String> comboPuesto = new ComboBox<>();
        comboPuesto.getItems().addAll("Vendedor", "Mecánico", "Gerente", "Administrativo", "Recepcionista", "RH");
        comboPuesto.setPrefWidth(250);
        comboPuesto.setId("puesto");
        panelRaiz.add(comboPuesto, 1, fila);
        fila++;

        panelRaiz.add(new Label("Teléfono:"), 0, fila);
        TextField campoTelefono = new TextField();
        campoTelefono.setId("telefono");
        campoTelefono.setPrefWidth(250);
        campoTelefono.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("\\d*")) {
                campoTelefono.setText(oldVal);
            }
        });
        panelRaiz.add(campoTelefono, 1, fila);
        fila++;

        panelRaiz.add(new Label("Email:"), 0, fila);
        TextField campoEmail = new TextField();
        campoEmail.setId("email");
        campoEmail.setPrefWidth(250);
        panelRaiz.add(campoEmail, 1, fila);
        fila++;

        panelRaiz.add(new Label("Salario ($):"), 0, fila);
        TextField campoSalario = new TextField();
        campoSalario.setId("salario");
        campoSalario.setPrefWidth(250);
        campoSalario.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("\\d*(\\.\\d*)?")) {
                campoSalario.setText(oldVal);
            }
        });
        panelRaiz.add(campoSalario, 1, fila);
        fila++;

        panelRaiz.add(new Label("Fecha Contratación*:"), 0, fila);
        DatePicker selectorFechaContratacion = new DatePicker();
        selectorFechaContratacion.setValue(LocalDate.now());
        selectorFechaContratacion.setPrefWidth(250);
        selectorFechaContratacion.setId("fechaContratacion");
        panelRaiz.add(selectorFechaContratacion, 1, fila);
        fila++;

        panelRaiz.add(new Label("Estado*:"), 0, fila);
        ComboBox<String> comboEstado = new ComboBox<>();
        comboEstado.getItems().addAll("A (Activo)", "NA (No Activo)");
        comboEstado.setValue("A (Activo)");
        comboEstado.setPrefWidth(250);
        comboEstado.setId("estado");
        panelRaiz.add(comboEstado, 1, fila);

        HBox panelBotones = new HBox(20);
        panelBotones.setAlignment(Pos.CENTER);
        panelBotones.setPadding(new Insets(20, 0, 0, 0));

        Button botonRegistrar = new Button("Registrar Empleado");
        botonRegistrar.setStyle("-fx-font-weight: bold; -fx-padding: 10 25;");
        botonRegistrar.setOnAction(e -> procesarRegistro());

        Button botonRegistrarYLimpiar = new Button("Registrar y Nuevo");
        botonRegistrarYLimpiar.setStyle("-fx-font-weight: bold; -fx-padding: 10 25; -fx-background-color: #28a745; -fx-text-fill: white;");
        botonRegistrarYLimpiar.setOnAction(e -> {
            if (procesarRegistroConExito()) {
                limpiarFormulario();
            }
        });

        Button botonCancelar = new Button("Cancelar");
        botonCancelar.setOnAction(e -> stage.close());

        Button botonLimpiar = new Button("Limpiar");
        botonLimpiar.setOnAction(e -> limpiarFormulario());

        panelBotones.getChildren().addAll(botonRegistrar, botonRegistrarYLimpiar, botonLimpiar, botonCancelar);
        GridPane.setConstraints(panelBotones, 0, fila + 1, 2, 1);

        panelRaiz.getChildren().addAll(titulo, panelBotones);

        Scene scene = new Scene(panelRaiz, 500, 600);
        stage.setScene(scene);
        stage.setTitle("Agregar Empleado - RH");
        stage.setResizable(false);

        try {
            scene.getStylesheets().add(
                    getClass().getResource("/styles/estilos.css").toExternalForm()
            );
        } catch (Exception e) {
            System.err.println("No se pudo cargar CSS: " + e.getMessage());
        }
    }

    private boolean procesarRegistroConExito() {
        try {
            TextField campoIdEmpleado = (TextField) panelRaiz.lookup("#idEmpleado");
            TextField campoNombre = (TextField) panelRaiz.lookup("#nombre");
            TextField campoApellidoPaterno = (TextField) panelRaiz.lookup("#apellidoPaterno");
            TextField campoApellidoMaterno = (TextField) panelRaiz.lookup("#apellidoMaterno");
            ComboBox<String> comboPuesto = (ComboBox<String>) panelRaiz.lookup("#puesto");
            TextField campoTelefono = (TextField) panelRaiz.lookup("#telefono");
            TextField campoEmail = (TextField) panelRaiz.lookup("#email");
            TextField campoSalario = (TextField) panelRaiz.lookup("#salario");
            DatePicker selectorFechaContratacion = (DatePicker) panelRaiz.lookup("#fechaContratacion");
            ComboBox<String> comboEstado = (ComboBox<String>) panelRaiz.lookup("#estado");

            if (campoIdEmpleado.getText().trim().isEmpty()) {
                mostrarError("ID Empleado es obligatorio");
                campoIdEmpleado.requestFocus();
                return false;
            }

            if (campoNombre.getText().trim().isEmpty()) {
                mostrarError("Nombre es obligatorio");
                campoNombre.requestFocus();
                return false;
            }

            if (campoApellidoPaterno.getText().trim().isEmpty()) {
                mostrarError("Apellido Paterno es obligatorio");
                campoApellidoPaterno.requestFocus();
                return false;
            }

            BigDecimal salario = campoSalario.getText().isEmpty() ?
                    BigDecimal.ZERO : new BigDecimal(campoSalario.getText());

            String estadoCompleto = comboEstado.getValue();
            String estado = "A";
            if (estadoCompleto != null && estadoCompleto.startsWith("NA")) {
                estado = "NA";
            }

            boolean exito = controller.registrarEmpleado(
                    campoIdEmpleado.getText().trim(),
                    campoNombre.getText().trim(),
                    campoApellidoPaterno.getText().trim(),
                    campoApellidoMaterno.getText().trim(),
                    comboPuesto.getValue() != null ? comboPuesto.getValue() : "",
                    campoTelefono.getText().trim(),
                    campoEmail.getText().trim(),
                    salario,
                    selectorFechaContratacion.getValue(),
                    estado
            );

            if (exito) {
                mostrarMensajeExito("Empleado registrado correctamente.");
                return true;
            } else {
                mostrarError("No se pudo registrar el empleado");
                return false;
            }

        } catch (NumberFormatException e) {
            mostrarError("Formato de salario inválido. Use números con punto decimal");
            return false;
        } catch (Exception e) {
            mostrarError("Error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private void procesarRegistro() {
        if (procesarRegistroConExito()) {
            // Opcional: Puedes dejar que el usuario decida si cerrar o no
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Registro Exitoso");
            alert.setHeaderText(null);
            alert.setContentText("¿Desea registrar otro empleado?");

            ButtonType btnSi = new ButtonType("Sí, registrar otro");
            ButtonType btnNo = new ButtonType("No, cerrar");
            alert.getButtonTypes().setAll(btnSi, btnNo);

            alert.showAndWait().ifPresent(respuesta -> {
                if (respuesta == btnSi) {
                    limpiarFormulario();
                } else {
                    stage.close();
                }
            });
        }
    }

    private void limpiarFormulario() {
        // Limpiar solo los campos que deben ser llenados de nuevo
        TextField campoIdEmpleado = (TextField) panelRaiz.lookup("#idEmpleado");
        TextField campoNombre = (TextField) panelRaiz.lookup("#nombre");
        TextField campoApellidoPaterno = (TextField) panelRaiz.lookup("#apellidoPaterno");
        TextField campoApellidoMaterno = (TextField) panelRaiz.lookup("#apellidoMaterno");
        TextField campoTelefono = (TextField) panelRaiz.lookup("#telefono");
        TextField campoEmail = (TextField) panelRaiz.lookup("#email");
        TextField campoSalario = (TextField) panelRaiz.lookup("#salario");

        if (campoIdEmpleado != null) campoIdEmpleado.clear();
        if (campoNombre != null) campoNombre.clear();
        if (campoApellidoPaterno != null) campoApellidoPaterno.clear();
        if (campoApellidoMaterno != null) campoApellidoMaterno.clear();
        if (campoTelefono != null) campoTelefono.clear();
        if (campoEmail != null) campoEmail.clear();
        if (campoSalario != null) campoSalario.clear();

        // Mantener valores por defecto
        ComboBox<String> comboPuesto = (ComboBox<String>) panelRaiz.lookup("#puesto");
        if (comboPuesto != null) {
            comboPuesto.setValue(null);
        }

        DatePicker selectorFechaContratacion = (DatePicker) panelRaiz.lookup("#fechaContratacion");
        if (selectorFechaContratacion != null) {
            selectorFechaContratacion.setValue(LocalDate.now());
        }

        ComboBox<String> comboEstado = (ComboBox<String>) panelRaiz.lookup("#estado");
        if (comboEstado != null) {
            comboEstado.setValue("A (Activo)");
        }

        // Enfocar el primer campo para nuevo registro
        if (campoIdEmpleado != null) {
            campoIdEmpleado.requestFocus();
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
        alert.setTitle("Registro Exitoso");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.initOwner(stage);
        alert.showAndWait();
    }
}