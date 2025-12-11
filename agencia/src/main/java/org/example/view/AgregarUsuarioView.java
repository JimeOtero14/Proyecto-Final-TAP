package org.example.view;

import org.example.controller.UsuarioController;
import org.example.dao.UsuarioDAOImpl;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.List;

public class AgregarUsuarioView {
    private Stage stage;
    private UsuarioController controller;
    private UsuarioDAOImpl usuarioDAO;
    private ComboBox<String> comboEmpleados;
    private TextField campoUsername;
    private PasswordField campoPassword;
    private PasswordField campoConfirmar;
    private ComboBox<String> comboNivel;

    public AgregarUsuarioView(Stage stage) {
        this.stage = stage;
        this.controller = new UsuarioController();
        this.usuarioDAO = new UsuarioDAOImpl();
        crearVista();
        cargarEmpleadosSinUsuario();
    }

    private void crearVista() {
        GridPane panelRaiz = new GridPane();
        panelRaiz.setPadding(new Insets(20));
        panelRaiz.setHgap(15);
        panelRaiz.setVgap(10);
        panelRaiz.setAlignment(Pos.CENTER);

        Label titulo = new Label("AGREGAR NUEVO USUARIO");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        GridPane.setConstraints(titulo, 0, 0, 2, 1);

        int fila = 1;

        // Empleado
        panelRaiz.add(new Label("Empleado*:"), 0, fila);
        comboEmpleados = new ComboBox<>();
        comboEmpleados.setPrefWidth(350);
        comboEmpleados.setPromptText("Seleccione empleado sin cuenta...");
        GridPane.setConstraints(comboEmpleados, 1, fila);
        fila++;

        // Username
        panelRaiz.add(new Label("Username*:"), 0, fila);
        campoUsername = new TextField();
        campoUsername.setPrefWidth(250);
        GridPane.setConstraints(campoUsername, 1, fila);
        fila++;

        // Contraseña
        panelRaiz.add(new Label("Contraseña*:"), 0, fila);
        campoPassword = new PasswordField();
        campoPassword.setPrefWidth(250);
        GridPane.setConstraints(campoPassword, 1, fila);
        fila++;

        // Confirmar Contraseña
        panelRaiz.add(new Label("Confirmar Contraseña*:"), 0, fila);
        campoConfirmar = new PasswordField();
        campoConfirmar.setPrefWidth(250);
        GridPane.setConstraints(campoConfirmar, 1, fila);
        fila++;

        // Nivel de Acceso
        panelRaiz.add(new Label("Nivel de Acceso*:"), 0, fila);
        comboNivel = new ComboBox<>();
        comboNivel.getItems().addAll("ADMIN", "GERENTE", "MECANICO", "VENDEDOR", "RH");
        comboNivel.setPrefWidth(250);
        GridPane.setConstraints(comboNivel, 1, fila);

        // Panel de botones
        HBox panelBotones = new HBox(20);
        panelBotones.setAlignment(Pos.CENTER);
        panelBotones.setPadding(new Insets(20, 0, 0, 0));


        Button btnRegistrarYLimpiar = new Button("Registrar");
        btnRegistrarYLimpiar.setStyle("-fx-font-weight: bold; -fx-padding: 10 25; -fx-background-color: #28a745; -fx-text-fill: white;");
        btnRegistrarYLimpiar.setOnAction(e -> {
            if (procesarRegistro()) {
                limpiarFormulario();
                cargarEmpleadosSinUsuario();
            }
        });

        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setOnAction(e -> stage.close());

        Button btnLimpiar = new Button("Limpiar");
        btnLimpiar.setOnAction(e -> limpiarFormulario());

        panelBotones.getChildren().addAll(btnRegistrarYLimpiar, btnLimpiar, btnCancelar);
        GridPane.setConstraints(panelBotones, 0, fila + 1, 2, 1);

        // Agregar todos los componentes al panel raíz
        panelRaiz.getChildren().addAll(
                titulo, comboEmpleados, campoUsername, campoPassword,
                campoConfirmar, comboNivel, panelBotones
        );

        Scene scene = new Scene(panelRaiz, 600, 500);
        stage.setScene(scene);
        stage.setTitle("Agregar Usuario - Admin");
        stage.setResizable(false);
    }

    private void cargarEmpleadosSinUsuario() {
        List<String> empleados = usuarioDAO.obtenerEmpleadosSinUsuario();
        comboEmpleados.getItems().clear();
        comboEmpleados.getItems().addAll(empleados);

        if (empleados.isEmpty()) {
            comboEmpleados.setPromptText("No hay empleados sin cuenta");
            comboEmpleados.setDisable(true);
        } else {
            comboEmpleados.setDisable(false);
        }
    }

    private boolean procesarRegistro() {
        try {
            String empleadoSeleccionado = comboEmpleados.getValue();

            if (empleadoSeleccionado == null || empleadoSeleccionado.trim().isEmpty()) {
                mostrarError("Seleccione un empleado");
                return false;
            }

            String idEmpleado = empleadoSeleccionado.split(" - ")[0];

            if (campoUsername.getText().trim().isEmpty()) {
                mostrarError("Username es obligatorio");
                campoUsername.requestFocus();
                return false;
            }

            if (usuarioDAO.usernameExiste(campoUsername.getText().trim())) {
                mostrarError("El username ya está en uso. Elija otro.");
                campoUsername.requestFocus();
                return false;
            }

            if (campoPassword.getText().trim().isEmpty()) {
                mostrarError("Contraseña es obligatoria");
                campoPassword.requestFocus();
                return false;
            }

            if (campoPassword.getText().length() < 6) {
                mostrarError("La contraseña debe tener al menos 6 caracteres");
                campoPassword.requestFocus();
                return false;
            }

            if (!campoPassword.getText().equals(campoConfirmar.getText())) {
                mostrarError("Las contraseñas no coinciden");
                campoPassword.clear();
                campoConfirmar.clear();
                campoPassword.requestFocus();
                return false;
            }

            if (comboNivel.getValue() == null) {
                mostrarError("Seleccione un nivel de acceso");
                return false;
            }

            boolean exito = controller.registrarUsuario(
                    idEmpleado,
                    campoUsername.getText().trim(),
                    campoPassword.getText().trim(),
                    comboNivel.getValue()
            );

            if (exito) {
                mostrarMensajeExito("Usuario registrado correctamente.");
                return true;
            } else {
                mostrarError("No se pudo registrar el usuario");
                return false;
            }

        } catch (Exception e) {
            mostrarError("Error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private void limpiarFormulario() {
        // Limpiar campos del formulario
        campoUsername.clear();
        campoPassword.clear();
        campoConfirmar.clear();
        comboNivel.setValue(null);

        // Enfocar el primer campo
        campoUsername.requestFocus();
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
}