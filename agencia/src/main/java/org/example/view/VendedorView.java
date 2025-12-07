package org.example.view;

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
        btnConsultarInventario.setOnAction(e -> {
            System.out.println("VENDEDOR: Consultar inventario clickeado");
        });

        Button btnGestionarCliente = crearBotonGrande("Gestionar Cliente");
        btnGestionarCliente.setOnAction(e -> {
            System.out.println("VENDEDOR: Gestionar cliente clickeado");
        });

        Button btnProcesarVenta = crearBotonGrande("Procesar Venta");
        btnProcesarVenta.setOnAction(e -> {
            System.out.println("VENDEDOR: Procesar venta clickeado");
        });

        panelBotones.getChildren().addAll(btnConsultarInventario, btnGestionarCliente, btnProcesarVenta);
    }
}