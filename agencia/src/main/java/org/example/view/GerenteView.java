package org.example.view;

import org.example.controller.LoginController;
import org.example.model.Empleado;
import javafx.scene.control.Button;

public class GerenteView extends BaseView {

    public GerenteView(LoginController controlador, Empleado empleado) {
        super(controlador, empleado);
    }

    @Override
    protected void crearBotonesEspecificos() {
        panelBotones.getChildren().clear();

        Button btnConsultarInventario = crearBotonGrande("Consultar Inventario");
        btnConsultarInventario.setOnAction(e -> {
            System.out.println("GERENTE: Consultar inventario clickeado");
        });

        Button btnGestionarCliente = crearBotonGrande("Gestionar Cliente");
        btnGestionarCliente.setOnAction(e -> {
            System.out.println("GERENTE: Gestionar cliente clickeado");
        });

        Button btnConfigurarPrecios = crearBotonGrande("Configurar Precios");
        btnConfigurarPrecios.setOnAction(e -> {
            System.out.println("GERENTE: Configurar precios clickeado");
        });

        panelBotones.getChildren().addAll(btnConsultarInventario, btnGestionarCliente, btnConfigurarPrecios);
    }
}