package org.example.view;

import org.example.controller.UsuarioController;
import org.example.dao.EmpleadoDAO;
import org.example.dao.EmpleadoDAOImpl;
import org.example.dao.UsuarioDAOImpl;
import org.example.model.Usuario;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.List;

public class EliminarUsuarioView {
    private Stage stage;
    private UsuarioController controller;
    private UsuarioDAOImpl usuarioDAO;
    private EmpleadoDAO empleadoDAO;
    private ComboBox<Usuario> comboUsuarios;
    private VBox panelInfo;
    private Label lblUsername;
    private Label lblNivel;
    private Label lblEmpleado;

    // Declarar panelRaiz como campo de clase
    private GridPane panelRaiz;

    public EliminarUsuarioView(Stage stage) {
        this.stage = stage;
        this.controller = new UsuarioController();
        this.usuarioDAO = new UsuarioDAOImpl();
        this.empleadoDAO = new EmpleadoDAOImpl();
        crearVista();
        cargarUsuarios();
    }

    private void crearVista() {
        panelRaiz = new GridPane();
        panelRaiz.setPadding(new Insets(20));
        panelRaiz.setHgap(15);
        panelRaiz.setVgap(10);
        panelRaiz.setAlignment(Pos.CENTER);

        Label titulo = new Label("DAR DE BAJA USUARIO");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #dc3545;");
        GridPane.setConstraints(titulo, 0, 0, 2, 1);

        Label advertencia = new Label("⚠ Esta acción eliminará la cuenta y marcará al empleado como 'No Activo'");
        advertencia.setStyle("-fx-font-size: 12px; -fx-text-fill: #856404;");
        GridPane.setConstraints(advertencia, 0, 1, 2, 1);

        int fila = 2;

        // Combo de usuarios
        panelRaiz.add(new Label("Seleccione Usuario*:"), 0, fila);
        comboUsuarios = new ComboBox<>();
        comboUsuarios.setPrefWidth(350);
        comboUsuarios.setPromptText("Seleccione usuario a eliminar...");
        comboUsuarios.setCellFactory(lv -> new ListCell<Usuario>() {
            @Override
            protected void updateItem(Usuario usuario, boolean empty) {
                super.updateItem(usuario, empty);
                setText(empty ? "" : usuario.getUsername() + " - " + usuario.getNivel());
            }
        });
        GridPane.setConstraints(comboUsuarios, 1, fila);
        fila++;

        // Panel de información
        panelInfo = new VBox(10);
        panelInfo.setPadding(new Insets(15));
        panelInfo.setStyle("-fx-border-color: #f8d7da; -fx-border-width: 2px; -fx-background-color: #f8d7da;");
        panelInfo.setVisible(false);

        Label lblInfoTitulo = new Label("Información del Usuario a Eliminar:");
        lblInfoTitulo.setStyle("-fx-font-weight: bold; -fx-text-fill: #721c24;");

        lblUsername = new Label();
        lblNivel = new Label();
        lblEmpleado = new Label();

        Label lblConsecuencias = new Label("Consecuencias:\n• Cuenta eliminada permanentemente\n• Empleado marcado como 'No Activo'\n• No podrá acceder al sistema");
        lblConsecuencias.setStyle("-fx-text-fill: #721c24;");

        panelInfo.getChildren().addAll(lblInfoTitulo, lblUsername, lblNivel, lblEmpleado, lblConsecuencias);
        GridPane.setConstraints(panelInfo, 0, fila, 2, 1);
        fila++;

        // Configurar evento del combo
        comboUsuarios.setOnAction(e -> mostrarInformacionUsuario());

        // Panel de botones
        HBox panelBotones = new HBox(20);
        panelBotones.setAlignment(Pos.CENTER);
        panelBotones.setPadding(new Insets(20, 0, 0, 0));

        Button btnEliminarYLimpiar = new Button("Dar de Baja");
        btnEliminarYLimpiar.setStyle("-fx-font-weight: bold; -fx-padding: 10 25; -fx-background-color: #dc3545; -fx-text-fill: white;");
        btnEliminarYLimpiar.setOnAction(e -> {
            if (eliminarUsuario()) {
                limpiarFormulario();
            }
        });

        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setOnAction(e -> stage.close());

        panelBotones.getChildren().addAll(btnEliminarYLimpiar, btnCancelar);
        GridPane.setConstraints(panelBotones, 0, fila + 1, 2, 1);

        // Agregar todos los componentes al panel raíz
        panelRaiz.getChildren().addAll(
                titulo, advertencia, comboUsuarios, panelInfo, panelBotones
        );

        Scene scene = new Scene(panelRaiz, 650, 550);
        stage.setScene(scene);
        stage.setTitle("Dar de Baja Usuario - Admin");
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

    private void mostrarInformacionUsuario() {
        Usuario usuario = comboUsuarios.getValue();

        if (usuario == null) {
            panelInfo.setVisible(false);
            return;
        }

        lblUsername.setText("Username: " + usuario.getUsername());
        lblNivel.setText("Nivel: " + usuario.getNivel());
        lblEmpleado.setText("ID Empleado: " + usuario.getIdEmpleado());

        panelInfo.setVisible(true);
    }

    private boolean eliminarUsuario() {
        Usuario usuario = comboUsuarios.getValue();

        if (usuario == null) {
            mostrarError("Seleccione un usuario primero");
            return false;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar Eliminación");
        confirmacion.setHeaderText("¿Está seguro de dar de baja este usuario?");
        confirmacion.setContentText("Usuario: " + usuario.getUsername() +
                "\nNivel: " + usuario.getNivel() +
                "\nID Empleado: " + usuario.getIdEmpleado() +
                "\n\n⚠ Esta acción NO se puede deshacer.");

        return confirmacion.showAndWait().map(respuesta -> {
            if (respuesta == ButtonType.OK) {
                try {
                    // 1. Eliminar el usuario
                    boolean usuarioEliminado = usuarioDAO.eliminarUsuario(usuario.getIdUsuario());

                    // 2. Marcar empleado como No Activo
                    boolean empleadoActualizado = empleadoDAO.cambiarEstadoEmpleado(
                            usuario.getIdEmpleado(), "NA");

                    if (usuarioEliminado && empleadoActualizado) {
                        mostrarMensaje("Usuario dado de baja correctamente\nEmpleado marcado como 'No Activo'");
                        return true;
                    } else {
                        mostrarError("Ocurrió un error al procesar la baja");
                        return false;
                    }

                } catch (Exception e) {
                    mostrarError("Error: " + e.getMessage());
                    e.printStackTrace();
                    return false;
                }
            }
            return false;
        }).orElse(false);
    }

    private void limpiarFormulario() {
        // Limpiar selección
        comboUsuarios.setValue(null);

        // Ocultar panel de información
        panelInfo.setVisible(false);

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