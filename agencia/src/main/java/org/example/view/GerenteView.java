package org.example.view;

import javafx.stage.Stage;
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
        btnConsultarInventario.getStyleClass().add("gerente-button");
        btnConsultarInventario.setOnAction(e -> {
            System.out.println("GERENTE: Consultar inventario clickeado");
        });

        Button btnGestionarCliente = crearBotonGrande("Gestionar Cliente");
        btnGestionarCliente.getStyleClass().add("gerente-button");
        btnGestionarCliente.setOnAction(e -> {
            System.out.println("GERENTE: Gestionar cliente clickeado");
        });

        Button btnConfigurarPrecios = crearBotonGrande("Configurar Precios");
        btnConfigurarPrecios.getStyleClass().add("gerente-button");
        btnConfigurarPrecios.setOnAction(e -> {
            System.out.println("GERENTE: Configurar precios clickeado");
        });

        Button btnAgregarProveedor = crearBotonGrande("Agregar Proveedor");
        btnAgregarProveedor.getStyleClass().add("gerente-button");
        btnAgregarProveedor.setOnAction(e -> {
            Stage stageActual = (Stage) btnAgregarProveedor.getScene().getWindow();
            stageActual.close();
            Stage nuevaVentana = new Stage();
            AgregarProveedorView vistaProveedor = new AgregarProveedorView(nuevaVentana, empleadoActual);
            nuevaVentana.show();
        });

        Button btnAgregarDocumento = crearBotonGrande("Agregar Documentos");
        btnAgregarDocumento.getStyleClass().add("gerente-button");
        btnAgregarDocumento.setOnAction(e -> {
            Stage stageActual = (Stage) btnAgregarDocumento.getScene().getWindow();
            stageActual.close();
            Stage nuevaVentana = new Stage();
            AgregarDocumentoView vistaDocumento = new AgregarDocumentoView(nuevaVentana, empleadoActual);
            nuevaVentana.show();
        });

        panelBotones.getChildren().addAll(btnConsultarInventario, btnGestionarCliente, btnConfigurarPrecios,btnAgregarProveedor,btnAgregarDocumento);
    }
}