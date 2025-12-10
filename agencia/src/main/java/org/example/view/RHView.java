
package org.example.view;

import javafx.stage.Stage;
import org.example.controller.LoginController;
import org.example.model.Empleado;
import javafx.scene.control.Button;

public class RHView extends BaseView {

    public RHView(LoginController controlador, Empleado empleado) {
        super(controlador, empleado);
    }

    @Override
    protected void crearBotonesEspecificos() {
        panelBotones.getChildren().clear();

        Button btnAgregarEmpleado = crearBotonGrande("Agregar Empleado");
        btnAgregarEmpleado.getStyleClass().add("admin-button");
        btnAgregarEmpleado.setOnAction(e -> {
            Stage stageActual = (Stage) btnAgregarEmpleado.getScene().getWindow();
            stageActual.close();

            Stage nuevaVentana = new Stage();
            AgregarEmpleadoView vistaAgregar = new AgregarEmpleadoView(nuevaVentana);
            nuevaVentana.show();
        });

        Button btnConsultarEmpleados = crearBotonGrande("Consultar Empleados");
        btnConsultarEmpleados.getStyleClass().add("admin-button");
        btnConsultarEmpleados.setOnAction(e -> {
            Stage stageActual = (Stage) btnConsultarEmpleados.getScene().getWindow();
            stageActual.close();
            Stage nuevaVentana = new Stage();
            ConsultarEmpleadosView vistaConsulta = new ConsultarEmpleadosView(nuevaVentana);
            nuevaVentana.show();
        });


        panelBotones.getChildren().addAll(btnAgregarEmpleado, btnConsultarEmpleados);
    }
}