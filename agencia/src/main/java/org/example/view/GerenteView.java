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
            Stage stageActual = (Stage) btnConsultarInventario.getScene().getWindow();
            stageActual.close();
            Stage nuevaVentana = new Stage();
            InventarioView vistaInventario = new InventarioView(nuevaVentana, true); // true = es gerente
            nuevaVentana.show();
        });

        Button btnGestionarCliente = crearBotonGrande("Gestionar Clientes");
        btnGestionarCliente.getStyleClass().add("gerente-button");
        btnGestionarCliente.setOnAction(e -> {
            Stage stageActual = (Stage) btnGestionarCliente.getScene().getWindow();
            stageActual.close();
            Stage nuevaVentana = new Stage();
            ConsultarClientesView vistaClientes = new ConsultarClientesView(nuevaVentana, true); // true = es gerente
            nuevaVentana.show();
        });

        Button btnAgregarCliente = crearBotonGrande("Agregar Cliente");
        btnAgregarCliente.getStyleClass().add("gerente-button");
        btnAgregarCliente.setOnAction(e -> {
            Stage stageActual = (Stage) btnAgregarCliente.getScene().getWindow();
            stageActual.close();
            Stage nuevaVentana = new Stage();
            AgregarClienteView vistaAgregar = new AgregarClienteView(nuevaVentana, empleadoActual);
            nuevaVentana.show();
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

        panelBotones.getChildren().addAll(btnConsultarInventario, btnGestionarCliente, btnAgregarCliente, btnConfigurarPrecios,btnAgregarProveedor,btnAgregarDocumento);
    }
}