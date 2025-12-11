package org.example.view;

import javafx.stage.Stage;
import org.example.controller.LoginController;
import org.example.model.Empleado;
import javafx.scene.control.Button;

public class VendedorView extends BaseView {

    public VendedorView(LoginController controlador, Empleado empleado) {
        super(controlador, empleado);
    }

    @Override
    protected void crearBotonesEspecificos() {
        panelBotones.getChildren().clear();

        Button btnConsultarInventario = crearBotonGrande("Consultar Inventario");
        btnConsultarInventario.getStyleClass().add("vendedor-button");
        btnConsultarInventario.setOnAction(e -> {
            Stage stageActual = (Stage) btnConsultarInventario.getScene().getWindow();
            stageActual.close();
            Stage nuevaVentana = new Stage();
            InventarioView vistaInventario = new InventarioView(nuevaVentana, false); // false = es vendedor
            nuevaVentana.show();
        });

        Button btnConsultarClientes = crearBotonGrande("Consultar Clientes");
        btnConsultarClientes.getStyleClass().add("vendedor-button");
        btnConsultarClientes.setOnAction(e -> {
            Stage stageActual = (Stage) btnConsultarClientes.getScene().getWindow();
            stageActual.close();
            Stage nuevaVentana = new Stage();
            ConsultarClientesView vistaClientes = new ConsultarClientesView(nuevaVentana, false); // false = no es gerente
            nuevaVentana.show();
        });


        Button btnProcesarVenta = crearBotonGrande("Procesar Venta");
        btnProcesarVenta.getStyleClass().add("vendedor-button");
        btnProcesarVenta.setOnAction(e -> {
            System.out.println("VENDEDOR: Procesar venta clickeado");
        });

        panelBotones.getChildren().addAll(btnConsultarInventario, btnConsultarClientes, btnProcesarVenta);
    }
}