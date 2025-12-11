package org.example.view;

import javafx.stage.Stage;
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
        btnRegistrarVehiculo.getStyleClass().add("mecanico-button");
        btnRegistrarVehiculo.setOnAction(e -> {
            Stage stageActual = (Stage) btnRegistrarVehiculo.getScene().getWindow();
            stageActual.close();
            Stage nuevaVentana = new Stage();
            RegistroVehiculoView vistaRegistro = new RegistroVehiculoView(nuevaVentana, empleadoActual);
            nuevaVentana.show();
        });

        Button btnRegistrarMantenimiento = crearBotonGrande("Registrar Mantenimiento");
        btnRegistrarMantenimiento.getStyleClass().add("mecanico-button");
        btnRegistrarMantenimiento.setOnAction(e -> {
            Stage stageActual = (Stage) btnRegistrarMantenimiento.getScene().getWindow();
            stageActual.close();
            Stage nuevaVentana = new Stage();
            RegistrarMantenimientoView vistaMantenimiento = new RegistrarMantenimientoView(nuevaVentana, empleadoActual);
            nuevaVentana.show();
        });

        panelBotones.getChildren().addAll(btnRegistrarVehiculo, btnRegistrarMantenimiento);
    }
}