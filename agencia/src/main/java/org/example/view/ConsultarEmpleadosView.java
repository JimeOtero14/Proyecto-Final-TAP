// ConsultarEmpleadosView.java
package org.example.view;

import org.example.controller.EmpleadoController;
import org.example.dao.EmpleadoDAO;
import org.example.dao.EmpleadoDAOImpl;
import org.example.model.Empleado;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ConsultarEmpleadosView {
    private Stage stage;
    private EmpleadoController controller;
    private EmpleadoDAO empleadoDAO;
    private VBox panelRaiz;
    private ComboBox<Empleado> comboEmpleados;
    private VBox panelDetalles;
    private Empleado empleadoSeleccionado;

    public ConsultarEmpleadosView(Stage stage) {
        this.stage = stage;
        this.controller = new EmpleadoController();
        this.empleadoDAO = new EmpleadoDAOImpl();
        crearVista();
        cargarEmpleados();
    }

    private void crearVista() {
        panelRaiz = new VBox(20);
        panelRaiz.setPadding(new Insets(20));
        panelRaiz.setAlignment(Pos.TOP_CENTER);

        Label titulo = new Label("GESTIÓN DE EMPLEADOS");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label subtitulo = new Label("Seleccione un empleado para gestionar:");
        subtitulo.setStyle("-fx-font-size: 14px;");

        comboEmpleados = new ComboBox<>();
        comboEmpleados.setPrefWidth(400);
        comboEmpleados.setPromptText("Seleccione un empleado...");
        comboEmpleados.setCellFactory(lv -> new ListCell<Empleado>() {
            @Override
            protected void updateItem(Empleado empleado, boolean empty) {
                super.updateItem(empleado, empty);
                setText(empty ? "" : empleado.getIdEmpleado() + " - " +
                        empleado.getNombreCompleto() + " (" + empleado.getPuesto() + ")");
            }
        });
        comboEmpleados.setButtonCell(new ListCell<Empleado>() {
            @Override
            protected void updateItem(Empleado empleado, boolean empty) {
                super.updateItem(empleado, empty);
                setText(empty ? "" : empleado.getIdEmpleado() + " - " +
                        empleado.getNombreCompleto() + " (" + empleado.getPuesto() + ")");
            }
        });

        comboEmpleados.setOnAction(e -> mostrarDetallesEmpleado());

        panelDetalles = new VBox(15);
        panelDetalles.setPadding(new Insets(20));
        panelDetalles.setStyle("-fx-border-color: lightgray; -fx-border-radius: 5px; -fx-padding: 15;");
        panelDetalles.setVisible(false);

        HBox panelBotones = new HBox(20);
        panelBotones.setAlignment(Pos.CENTER);

        Button btnEditar = new Button("Editar Empleado");
        btnEditar.setStyle("-fx-font-weight: bold; -fx-padding: 10 25;");
        btnEditar.setOnAction(e -> abrirEditarEmpleado());

        Button btnEliminar = new Button("Eliminar Empleado");
        btnEliminar.setStyle("-fx-font-weight: bold; -fx-padding: 10 25; -fx-background-color: #dc3545; -fx-text-fill: white;");
        btnEliminar.setOnAction(e -> eliminarEmpleado());

        Button btnVolver = new Button("Volver");
        btnVolver.setStyle("-fx-padding: 10 25;");
        btnVolver.setOnAction(e -> stage.close());

        panelBotones.getChildren().addAll(btnEditar, btnEliminar, btnVolver);

        panelRaiz.getChildren().addAll(titulo, subtitulo, comboEmpleados, panelDetalles, panelBotones);

        Scene scene = new Scene(panelRaiz, 700, 600);
        stage.setScene(scene);
        stage.setTitle("Consultar Empleados - RH");
        stage.setResizable(false);
    }

    private void cargarEmpleados() {
        List<Empleado> empleados = empleadoDAO.listarActivos();
        comboEmpleados.getItems().clear();
        comboEmpleados.getItems().addAll(empleados);

        if (empleados.isEmpty()) {
            mostrarMensaje("No hay empleados activos registrados");
        }
    }

    private void mostrarDetallesEmpleado() {
        empleadoSeleccionado = comboEmpleados.getValue();

        if (empleadoSeleccionado == null) {
            panelDetalles.setVisible(false);
            return;
        }

        panelDetalles.getChildren().clear();

        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(10);

        grid.add(new Label("ID Empleado:"), 0, 0);
        grid.add(new Label(empleadoSeleccionado.getIdEmpleado()), 1, 0);

        grid.add(new Label("Nombre:"), 0, 1);
        grid.add(new Label(empleadoSeleccionado.getNombreCompleto()), 1, 1);

        grid.add(new Label("Puesto:"), 0, 2);
        grid.add(new Label(empleadoSeleccionado.getPuesto()), 1, 2);

        grid.add(new Label("Teléfono:"), 0, 3);
        grid.add(new Label(empleadoSeleccionado.getTelefono() != null ? empleadoSeleccionado.getTelefono() : "N/A"), 1, 3);

        grid.add(new Label("Email:"), 0, 4);
        grid.add(new Label(empleadoSeleccionado.getEmail() != null ? empleadoSeleccionado.getEmail() : "N/A"), 1, 4);

        grid.add(new Label("Salario:"), 0, 5);
        grid.add(new Label("$" + (empleadoSeleccionado.getSalario() != null ? empleadoSeleccionado.getSalario().toString() : "0.00")), 1, 5);

        grid.add(new Label("Fecha Contratación:"), 0, 6);
        grid.add(new Label(empleadoSeleccionado.getFechaContratacion() != null ?
                empleadoSeleccionado.getFechaContratacion().toString() : "N/A"), 1, 6);

        grid.add(new Label("Estado:"), 0, 7);
        grid.add(new Label(empleadoSeleccionado.getEstado().equals("A") ? "Activo" : "No Activo"), 1, 7);

        panelDetalles.getChildren().add(grid);
        panelDetalles.setVisible(true);
    }

    private void abrirEditarEmpleado() {
        if (empleadoSeleccionado == null) {
            mostrarError("Seleccione un empleado primero");
            return;
        }

        stage.close();
        Stage nuevaVentana = new Stage();
        EditarEmpleadoView vistaEditar = new EditarEmpleadoView(nuevaVentana, empleadoSeleccionado);
        nuevaVentana.show();
    }

    private void eliminarEmpleado() {
        if (empleadoSeleccionado == null) {
            mostrarError("Seleccione un empleado primero");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar Eliminación");
        confirmacion.setHeaderText("¿Está seguro de eliminar al empleado?");
        confirmacion.setContentText("Empleado: " + empleadoSeleccionado.getNombreCompleto() +
                "\nID: " + empleadoSeleccionado.getIdEmpleado() +
                "\n\nEsta acción marcará al empleado como 'No Activo'.");

        confirmacion.showAndWait().ifPresent(respuesta -> {
            if (respuesta == ButtonType.OK) {
                boolean exito = empleadoDAO.cambiarEstadoEmpleado(
                        empleadoSeleccionado.getIdEmpleado(), "NA");

                if (exito) {
                    mostrarMensaje("Empleado marcado como No Activo");
                    cargarEmpleados();
                    panelDetalles.setVisible(false);
                } else {
                    mostrarError("No se pudo eliminar el empleado");
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
}