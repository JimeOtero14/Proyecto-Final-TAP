package org.example.view;

import org.example.controller.UsuarioController;
import org.example.dao.UsuarioDAOImpl;
import org.example.model.Usuario;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.List;

public class EditarUsuarioView {
    private Stage stage;
    private UsuarioController controller;
    private UsuarioDAOImpl usuarioDAO;
    private Usuario usuario;
    private ComboBox<Usuario> comboUsuarios;
    private TextField campoUsername;
    private PasswordField campoPassword;
    private PasswordField campoConfirmar;
    private ComboBox<String> comboNivel;
    private Button btnGuardarYLimpiar;

    public EditarUsuarioView(Stage stage) {
        this.stage = stage;
        this.controller = new UsuarioController();
        this.usuarioDAO = new UsuarioDAOImpl();
        crearVista();
        cargarUsuarios();
    }

    private void crearVista() {
        GridPane panelRaiz = new GridPane();
        panelRaiz.setPadding(new Insets(20));
        panelRaiz.setHgap(15);
        panelRaiz.setVgap(10);
        panelRaiz.setAlignment(Pos.CENTER);

        Label titulo = new Label("EDITAR USUARIO");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        GridPane.setConstraints(titulo, 0, 0, 2, 1);

        int fila = 1;

        // Combo de usuarios
        panelRaiz.add(new Label("Seleccione Usuario*:"), 0, fila);
        comboUsuarios = new ComboBox<>();
        comboUsuarios.setPrefWidth(350);
        comboUsuarios.setPromptText("Seleccione usuario a editar...");
        comboUsuarios.setCellFactory(lv -> new ListCell<Usuario>() {
            @Override
            protected void updateItem(Usuario usuario, boolean empty) {
                super.updateItem(usuario, empty);
                setText(empty ? "" : usuario.getUsername() + " - " + usuario.getNivel());
            }
        });
        comboUsuarios.setOnAction(e -> cargarDatosUsuario());
        GridPane.setConstraints(comboUsuarios, 1, fila);
        fila++;

        // Username
        panelRaiz.add(new Label("Nuevo Username:"), 0, fila);
        campoUsername = new TextField();
        campoUsername.setPrefWidth(250);
        campoUsername.setDisable(true);
        GridPane.setConstraints(campoUsername, 1, fila);
        fila++;

        // Contraseña
        panelRaiz.add(new Label("Nueva Contraseña:"), 0, fila);
        campoPassword = new PasswordField();
        campoPassword.setPrefWidth(250);
        campoPassword.setDisable(true);
        GridPane.setConstraints(campoPassword, 1, fila);
        fila++;

        // Confirmar Contraseña
        panelRaiz.add(new Label("Confirmar Contraseña:"), 0, fila);
        campoConfirmar = new PasswordField();
        campoConfirmar.setPrefWidth(250);
        campoConfirmar.setDisable(true);
        GridPane.setConstraints(campoConfirmar, 1, fila);
        fila++;

        // Nivel
        panelRaiz.add(new Label("Nivel de Acceso:"), 0, fila);
        comboNivel = new ComboBox<>();
        comboNivel.getItems().addAll("ADMIN", "GERENTE", "MECANICO", "VENDEDOR", "RH");
        comboNivel.setPrefWidth(250);
        comboNivel.setDisable(true);
        GridPane.setConstraints(comboNivel, 1, fila);

        // Panel de botones
        HBox panelBotones = new HBox(20);
        panelBotones.setAlignment(Pos.CENTER);
        panelBotones.setPadding(new Insets(20, 0, 0, 0));


        btnGuardarYLimpiar = new Button("Guardar");
        btnGuardarYLimpiar.setStyle("-fx-font-weight: bold; -fx-padding: 10 25; -fx-background-color: #28a745; -fx-text-fill: white;");
        btnGuardarYLimpiar.setDisable(true);
        btnGuardarYLimpiar.setOnAction(e -> {
            if (guardarCambios()) {
                limpiarFormulario();
            }
        });

        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setOnAction(e -> stage.close());

        panelBotones.getChildren().addAll(btnGuardarYLimpiar, btnCancelar);
        GridPane.setConstraints(panelBotones, 0, fila + 1, 2, 1);

        // Agregar todos los componentes
        panelRaiz.getChildren().addAll(
                titulo, comboUsuarios, campoUsername, campoPassword,
                campoConfirmar, comboNivel, panelBotones
        );

        Scene scene = new Scene(panelRaiz, 550, 500);
        stage.setScene(scene);
        stage.setTitle("Editar Usuario - Admin");
        stage.setResizable(false);
    }

    private void cargarUsuarios() {
        List<Usuario> usuarios = controller.listarUsuarios();
        comboUsuarios.getItems().clear();
        comboUsuarios.getItems().addAll(usuarios);

        if (usuarios.isEmpty()) {
            comboUsuarios.setPromptText("No hay usuarios registrados");
        }
    }

    private void cargarDatosUsuario() {
        usuario = comboUsuarios.getValue();

        if (usuario == null) return;

        campoUsername.setText(usuario.getUsername());
        campoUsername.setDisable(false);

        comboNivel.setValue(usuario.getNivel());
        comboNivel.setDisable(false);

        campoPassword.setDisable(false);
        campoConfirmar.setDisable(false);
        btnGuardarYLimpiar.setDisable(false);
    }

    private boolean guardarCambios() {
        try {
            String nuevoUsername = campoUsername.getText().trim();
            String nuevaPassword = campoPassword.getText().trim();

            if (nuevoUsername.isEmpty()) {
                mostrarError("Username es obligatorio");
                return false;
            }

            // Verificar si username cambió y si ya existe
            if (!nuevoUsername.equals(usuario.getUsername()) && usuarioDAO.usernameExiste(nuevoUsername)) {
                mostrarError("El username ya está en uso. Elija otro.");
                return false;
            }

            // Si se cambia la contraseña, validarla
            if (!nuevaPassword.isEmpty()) {
                if (nuevaPassword.length() < 6) {
                    mostrarError("La contraseña debe tener al menos 6 caracteres");
                    campoPassword.requestFocus();
                    return false;
                }

                if (!nuevaPassword.equals(campoConfirmar.getText().trim())) {
                    mostrarError("Las contraseñas no coinciden");
                    campoPassword.clear();
                    campoConfirmar.clear();
                    campoPassword.requestFocus();
                    return false;
                }
            } else {
                // Mantener contraseña actual si no se cambia
                nuevaPassword = usuario.getPassword();
            }

            usuario.setUsername(nuevoUsername);
            usuario.setPassword(nuevaPassword);
            usuario.setNivel(comboNivel.getValue());

            boolean exito = controller.actualizarUsuario(usuario);

            if (exito) {
                mostrarMensaje("Usuario actualizado correctamente.");
                return true;
            } else {
                mostrarError("No se pudo actualizar el usuario");
                return false;
            }

        } catch (Exception e) {
            mostrarError("Error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private void limpiarFormulario() {
        // Limpiar todos los campos
        comboUsuarios.setValue(null);
        usuario = null;

        campoUsername.clear();
        campoUsername.setDisable(true);

        campoPassword.clear();
        campoPassword.setDisable(true);

        campoConfirmar.clear();
        campoConfirmar.setDisable(true);

        comboNivel.setValue(null);
        comboNivel.setDisable(true);

        btnGuardarYLimpiar.setDisable(true);

        // Recargar lista de usuarios
        cargarUsuarios();
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.initOwner(stage);
        alert.showAndWait();
    }

    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.initOwner(stage);
        alert.showAndWait();
    }
}