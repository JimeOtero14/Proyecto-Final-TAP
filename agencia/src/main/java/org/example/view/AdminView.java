package org.example.view;

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
            System.out.println("ADMIN: Registrar usuario clickeado");
        });

        Button btnEditarUsuario = crearBotonGrande("Editar Usuario");
        btnEditarUsuario.getStyleClass().add("admin-button");
        btnEditarUsuario.setOnAction(e -> {
            System.out.println("ADMIN: Editar usuario clickeado");
        });

        Button btnDarBajaUsuario = crearBotonGrande("Dar de Baja Usuario");
        btnDarBajaUsuario.getStyleClass().add("admin-button");
        btnDarBajaUsuario.setOnAction(e -> {
            System.out.println("ADMIN: Dar baja usuario clickeado");
        });

        panelBotones.getChildren().addAll(btnRegistrarUsuario, btnEditarUsuario, btnDarBajaUsuario);
    }
}