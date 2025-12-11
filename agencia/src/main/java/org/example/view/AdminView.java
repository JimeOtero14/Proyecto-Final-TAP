package org.example.view;

import javafx.stage.Stage;
import org.example.controller.LoginController;
import org.example.model.Empleado;
import javafx.scene.control.Button;

public class AdminView extends BaseView {

    public AdminView(LoginController controlador, Empleado empleado) {
        super(controlador, empleado);

    }

    @Override
    protected void crearBotonesEspecificos() {
        panelBotones.getChildren().clear();

        Button btnRegistrarUsuario = crearBotonGrande("Registrar Usuario");
        btnRegistrarUsuario.getStyleClass().add("admin-button");
        btnRegistrarUsuario.setOnAction(e -> {
            Stage stageActual = (Stage) btnRegistrarUsuario.getScene().getWindow();
            stageActual.close();

            Stage nuevaVentana = new Stage();
            AgregarUsuarioView vistaAgregar = new AgregarUsuarioView(nuevaVentana);
            nuevaVentana.show();
        });

        Button btnEditarUsuario = crearBotonGrande("Editar Usuario");
        btnEditarUsuario.getStyleClass().add("admin-button");
        btnEditarUsuario.setOnAction(e -> {
            Stage stageActual = (Stage) btnEditarUsuario.getScene().getWindow();
            stageActual.close();

            Stage nuevaVentana = new Stage();
            EditarUsuarioView vistaEditar = new EditarUsuarioView(nuevaVentana);
            nuevaVentana.show();
        });

        Button btnDarBajaUsuario = crearBotonGrande("Dar de Baja Usuario");
        btnDarBajaUsuario.getStyleClass().add("admin-button");
        btnDarBajaUsuario.setOnAction(e -> {
            Stage stageActual = (Stage) btnDarBajaUsuario.getScene().getWindow();
            stageActual.close();

            Stage nuevaVentana = new Stage();
            EliminarUsuarioView vistaEliminar = new EliminarUsuarioView(nuevaVentana);
            nuevaVentana.show();
        });

        panelBotones.getChildren().addAll(btnRegistrarUsuario, btnEditarUsuario, btnDarBajaUsuario);
    }
}