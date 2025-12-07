package org.example.view;

import org.example.controller.LoginController;
import org.example.model.Empleado;
import javafx.scene.control.Button;

public class MecanicoView extends BaseView {

    public MecanicoView(LoginController controlador, Empleado empleado) {
        super(controlador, empleado);
    }

    @Override
    protected void crearBotonesEspecificos() {
        panelBotones.getChildren().clear();

        Button btnRegistrarVehiculo = crearBotonGrande("Registrar Vehículo");
        btnRegistrarVehiculo.setOnAction(e -> {
            System.out.println("MECANICO: Registrar vehículo clickeado");
        });

        Button btnRegistrarMantenimiento = crearBotonGrande("Registrar Mantenimiento");
        btnRegistrarMantenimiento.setOnAction(e -> {
            System.out.println("MECANICO: Registrar mantenimiento clickeado");
        });

        panelBotones.getChildren().addAll(btnRegistrarVehiculo, btnRegistrarMantenimiento);
    }
}