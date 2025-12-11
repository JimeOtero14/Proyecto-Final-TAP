package org.example.view;

import org.example.dao.VehiculoDAO;
import org.example.dao.VehiculoDAOImpl;
import org.example.model.Vehiculo;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.List;

public class InventarioView {
    private Stage stage;
    private VehiculoDAO vehiculoDAO;
    private TableView<Vehiculo> tablaVehiculos;
    private ImageView visorImagen;
    private boolean esGerente;

    public InventarioView(Stage stage, boolean esGerente) {
        this.stage = stage;
        this.vehiculoDAO = new VehiculoDAOImpl();
        this.esGerente = esGerente;
        crearVista();
        cargarVehiculos();
    }

    private void crearVista() {
        BorderPane panelRaiz = new BorderPane();
        panelRaiz.setPadding(new Insets(10));

        // Título
        Label titulo = new Label("INVENTARIO DE VEHÍCULOS");
        titulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        BorderPane.setAlignment(titulo, Pos.CENTER);
        BorderPane.setMargin(titulo, new Insets(0, 0, 10, 0));
        panelRaiz.setTop(titulo);

        // Panel central dividido
        SplitPane splitPane = new SplitPane();
        splitPane.setDividerPositions(0.6);

        // Panel izquierdo: Tabla de vehículos
        VBox panelTabla = new VBox(10);
        panelTabla.setPadding(new Insets(10));

        // Filtros
        HBox panelFiltros = new HBox(10);
        panelFiltros.setAlignment(Pos.CENTER_LEFT);

        ComboBox<String> comboEstado = new ComboBox<>();
        comboEstado.getItems().addAll("Todos", "Disponible", "Vendido", "Reservado", "Mantenimiento");
        comboEstado.setValue("Todos");
        comboEstado.setPrefWidth(150);

        ComboBox<String> comboMarca = new ComboBox<>();
        comboMarca.setPromptText("Marca");
        comboMarca.setPrefWidth(150);

        ComboBox<String> comboTipo = new ComboBox<>();
        comboTipo.getItems().addAll("Todos", "Sedán", "SUV", "Pickup", "Deportivo", "Van", "Motocicleta");
        comboTipo.setValue("Todos");
        comboTipo.setPrefWidth(150);

        Button btnFiltrar = new Button("Filtrar");
        btnFiltrar.setOnAction(e -> aplicarFiltros(comboEstado.getValue(), comboMarca.getValue(), comboTipo.getValue()));

        Button btnLimpiar = new Button("Limpiar");
        btnLimpiar.setOnAction(e -> {
            comboEstado.setValue("Todos");
            comboMarca.setValue(null);
            comboTipo.setValue("Todos");
            cargarVehiculos();
        });

        panelFiltros.getChildren().addAll(
                new Label("Estado:"), comboEstado,
                new Label("Marca:"), comboMarca,
                new Label("Tipo:"), comboTipo,
                btnFiltrar, btnLimpiar
        );

        // Crear tabla
        tablaVehiculos = new TableView<>();
        tablaVehiculos.setPrefHeight(400);

        // Columnas de la tabla
        TableColumn<Vehiculo, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getIdVehiculo()));
        colId.setPrefWidth(80);

        TableColumn<Vehiculo, String> colMarca = new TableColumn<>("Marca");
        colMarca.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getMarca()));
        colMarca.setPrefWidth(100);

        TableColumn<Vehiculo, String> colModelo = new TableColumn<>("Modelo");
        colModelo.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getModelo()));
        colModelo.setPrefWidth(100);

        TableColumn<Vehiculo, String> colAño = new TableColumn<>("Año");
        colAño.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(String.valueOf(cellData.getValue().getAño())));
        colAño.setPrefWidth(70);

        TableColumn<Vehiculo, String> colColor = new TableColumn<>("Color");
        colColor.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getColor()));
        colColor.setPrefWidth(80);

        TableColumn<Vehiculo, String> colPrecioVenta = new TableColumn<>("Precio Venta");
        colPrecioVenta.setCellValueFactory(cellData -> {
            String precio = cellData.getValue().getPrecioVenta() != null ?
                    String.format("$%,.2f", cellData.getValue().getPrecioVenta()) : "N/A";
            return new javafx.beans.property.SimpleStringProperty(precio);
        });
        colPrecioVenta.setPrefWidth(100);

        TableColumn<Vehiculo, String> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getEstadoVehiculo()));
        colEstado.setPrefWidth(100);

        TableColumn<Vehiculo, String> colKilometraje = new TableColumn<>("Kilometraje");
        colKilometraje.setCellValueFactory(cellData -> {
            String km = cellData.getValue().getKilometraje() != null ?
                    String.format("%,.0f km", cellData.getValue().getKilometraje()) : "N/A";
            return new javafx.beans.property.SimpleStringProperty(km);
        });
        colKilometraje.setPrefWidth(100);

        TableColumn<Vehiculo, String> colTipo = new TableColumn<>("Tipo");
        colTipo.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTipoVehiculo()));
        colTipo.setPrefWidth(80);

        tablaVehiculos.getColumns().addAll(colId, colMarca, colModelo, colAño, colColor,
                colPrecioVenta, colEstado, colKilometraje, colTipo);

        // Seleccionar item de la tabla
        tablaVehiculos.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        mostrarDetallesVehiculo(newValue);
                    }
                }
        );

        panelTabla.getChildren().addAll(panelFiltros, tablaVehiculos);

        // Panel derecho: Detalles e imagen
        VBox panelDetalles = new VBox(15);
        panelDetalles.setPadding(new Insets(10));
        panelDetalles.setStyle("-fx-border-color: #bdc3c7; -fx-border-radius: 5px;");

        Label lblDetallesTitulo = new Label("DETALLES DEL VEHÍCULO");
        lblDetallesTitulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2980b9;");

        // Visor de imagen
        visorImagen = new ImageView();
        visorImagen.setFitWidth(300);
        visorImagen.setFitHeight(200);
        visorImagen.setPreserveRatio(true);
        visorImagen.setStyle("-fx-border-color: #95a5a6; -fx-border-width: 1px;");

        // Panel de detalles
        GridPane gridDetalles = new GridPane();
        gridDetalles.setHgap(10);
        gridDetalles.setVgap(8);
        gridDetalles.setPadding(new Insets(10));

        // Detalles estáticos (se llenarán dinámicamente)
        gridDetalles.add(new Label("ID:"), 0, 0);
        Label lblId = new Label();
        gridDetalles.add(lblId, 1, 0);

        gridDetalles.add(new Label("Marca:"), 0, 1);
        Label lblMarca = new Label();
        gridDetalles.add(lblMarca, 1, 1);

        gridDetalles.add(new Label("Modelo:"), 0, 2);
        Label lblModelo = new Label();
        gridDetalles.add(lblModelo, 1, 2);

        gridDetalles.add(new Label("Año:"), 0, 3);
        Label lblAño = new Label();
        gridDetalles.add(lblAño, 1, 3);

        gridDetalles.add(new Label("Color:"), 0, 4);
        Label lblColor = new Label();
        gridDetalles.add(lblColor, 1, 4);

        gridDetalles.add(new Label("Estado:"), 0, 5);
        Label lblEstado = new Label();
        lblEstado.setStyle("-fx-font-weight: bold;");
        gridDetalles.add(lblEstado, 1, 5);

        gridDetalles.add(new Label("Precio Compra:"), 0, 6);
        Label lblPrecioCompra = new Label();
        gridDetalles.add(lblPrecioCompra, 1, 6);

        gridDetalles.add(new Label("Precio Venta:"), 0, 7);
        Label lblPrecioVenta = new Label();
        lblPrecioVenta.setStyle("-fx-font-weight: bold; -fx-text-fill: #27ae60;");
        gridDetalles.add(lblPrecioVenta, 1, 7);

        gridDetalles.add(new Label("Kilometraje:"), 0, 8);
        Label lblKilometraje = new Label();
        gridDetalles.add(lblKilometraje, 1, 8);

        gridDetalles.add(new Label("Combustible:"), 0, 9);
        Label lblCombustible = new Label();
        gridDetalles.add(lblCombustible, 1, 9);

        gridDetalles.add(new Label("Transmisión:"), 0, 10);
        Label lblTransmision = new Label();
        gridDetalles.add(lblTransmision, 1, 10);

        gridDetalles.add(new Label("Tipo:"), 0, 11);
        Label lblTipo = new Label();
        gridDetalles.add(lblTipo, 1, 11);

        // TextArea para descripción
        Label lblDescTitulo = new Label("Descripción:");
        lblDescTitulo.setStyle("-fx-font-weight: bold;");

        TextArea areaDescripcion = new TextArea();
        areaDescripcion.setPrefRowCount(4);
        areaDescripcion.setEditable(false);
        areaDescripcion.setWrapText(true);

        panelDetalles.getChildren().addAll(
                lblDetallesTitulo,
                visorImagen,
                gridDetalles,
                lblDescTitulo,
                areaDescripcion
        );

        // Guardar referencias para acceso dinámico
        panelDetalles.setUserData(new Object[]{lblId, lblMarca, lblModelo, lblAño, lblColor,
                lblEstado, lblPrecioCompra, lblPrecioVenta,
                lblKilometraje, lblCombustible, lblTransmision,
                lblTipo, areaDescripcion});

        splitPane.getItems().addAll(panelTabla, panelDetalles);
        panelRaiz.setCenter(splitPane);

        // Panel inferior con estadísticas
        HBox panelEstadisticas = new HBox(20);
        panelEstadisticas.setPadding(new Insets(10));
        panelEstadisticas.setAlignment(Pos.CENTER);
        panelEstadisticas.setStyle("-fx-background-color: #ecf0f1; -fx-border-radius: 5px;");

        Label lblTotal = new Label("Total vehículos: 0");
        lblTotal.setStyle("-fx-font-weight: bold;");

        Label lblDisponibles = new Label("Disponibles: 0");
        lblDisponibles.setStyle("-fx-font-weight: bold; -fx-text-fill: #27ae60;");

        Label lblVendidos = new Label("Vendidos: 0");
        lblVendidos.setStyle("-fx-font-weight: bold; -fx-text-fill: #e74c3c;");

        Label lblValorInventario = new Label("Valor inventario: $0.00");
        lblValorInventario.setStyle("-fx-font-weight: bold; -fx-text-fill: #2980b9;");

        panelEstadisticas.getChildren().addAll(lblTotal, lblDisponibles, lblVendidos, lblValorInventario);
        panelEstadisticas.setUserData(new Object[]{lblTotal, lblDisponibles, lblVendidos, lblValorInventario});

        panelRaiz.setBottom(panelEstadisticas);

        // Panel de botones
        HBox panelBotones = new HBox(20);
        panelBotones.setPadding(new Insets(10));
        panelBotones.setAlignment(Pos.CENTER);

        Button btnActualizar = new Button("Actualizar Inventario");
        btnActualizar.setStyle("-fx-font-weight: bold; -fx-background-color: #3498db; -fx-text-fill: white;");
        btnActualizar.setOnAction(e -> cargarVehiculos());

        Button btnExportar = new Button("Exportar a PDF");
        btnExportar.setOnAction(e -> exportarAPDF());

        Button btnVolver = new Button("Volver");
        btnVolver.setOnAction(e -> stage.close());

        panelBotones.getChildren().addAll(btnActualizar, btnExportar, btnVolver);
        panelRaiz.setBottom(new VBox(10, panelEstadisticas, panelBotones));

        Scene scene = new Scene(panelRaiz, 1100, 700);
        stage.setScene(scene);
        stage.setTitle("Inventario de Vehículos - " + (esGerente ? "Gerente" : "Vendedor"));
        stage.setResizable(true);
    }

    private void cargarVehiculos() {
        List<Vehiculo> vehiculos = vehiculoDAO.listarTodos();
        ObservableList<Vehiculo> listaVehiculos = FXCollections.observableArrayList(vehiculos);
        tablaVehiculos.setItems(listaVehiculos);

        actualizarEstadisticas(vehiculos);
    }

    private void aplicarFiltros(String estado, String marca, String tipo) {
        List<Vehiculo> todosVehiculos = vehiculoDAO.listarTodos();
        ObservableList<Vehiculo> listaFiltrada = FXCollections.observableArrayList();

        for (Vehiculo v : todosVehiculos) {
            boolean cumpleEstado = estado.equals("Todos") || estado.equals(v.getEstadoVehiculo());
            boolean cumpleMarca = marca == null || marca.isEmpty() || marca.equals(v.getMarca());
            boolean cumpleTipo = tipo.equals("Todos") || tipo.equals(v.getTipoVehiculo());

            if (cumpleEstado && cumpleMarca && cumpleTipo) {
                listaFiltrada.add(v);
            }
        }

        tablaVehiculos.setItems(listaFiltrada);
        actualizarEstadisticas(listaFiltrada);
    }

    private void mostrarDetallesVehiculo(Vehiculo vehiculo) {
        try {
            // Mostrar imagen
            if (vehiculo.getFoto() != null) {
                byte[] bytes = vehiculo.getFoto().getBytes(1, (int) vehiculo.getFoto().length());
                Image img = new Image(new java.io.ByteArrayInputStream(bytes));
                visorImagen.setImage(img);
            } else {
                // Imagen por defecto o placeholder
                visorImagen.setImage(null);
                visorImagen.setStyle("-fx-border-color: #95a5a6; -fx-border-width: 1px; -fx-background-color: #f8f9fa;");
            }

            // Actualizar detalles en el grid
            VBox panelDetalles = (VBox) ((SplitPane) ((BorderPane) stage.getScene().getRoot()).getCenter()).getItems().get(1);
            Object[] componentes = (Object[]) panelDetalles.getUserData();

            ((Label) componentes[0]).setText(vehiculo.getIdVehiculo());
            ((Label) componentes[1]).setText(vehiculo.getMarca());
            ((Label) componentes[2]).setText(vehiculo.getModelo());
            ((Label) componentes[3]).setText(String.valueOf(vehiculo.getAño()));
            ((Label) componentes[4]).setText(vehiculo.getColor() != null ? vehiculo.getColor() : "N/A");

            String estado = vehiculo.getEstadoVehiculo();
            ((Label) componentes[5]).setText(estado);

            // Color según estado
            switch(estado) {
                case "Disponible":
                    ((Label) componentes[5]).setStyle("-fx-font-weight: bold; -fx-text-fill: #27ae60;");
                    break;
                case "Vendido":
                    ((Label) componentes[5]).setStyle("-fx-font-weight: bold; -fx-text-fill: #e74c3c;");
                    break;
                case "Reservado":
                    ((Label) componentes[5]).setStyle("-fx-font-weight: bold; -fx-text-fill: #f39c12;");
                    break;
                case "Mantenimiento":
                    ((Label) componentes[5]).setStyle("-fx-font-weight: bold; -fx-text-fill: #3498db;");
                    break;
                default:
                    ((Label) componentes[5]).setStyle("-fx-font-weight: bold;");
            }

            ((Label) componentes[6]).setText(vehiculo.getPrecioCompra() != null ?
                    String.format("$%,.2f", vehiculo.getPrecioCompra()) : "N/A");
            ((Label) componentes[7]).setText(vehiculo.getPrecioVenta() != null ?
                    String.format("$%,.2f", vehiculo.getPrecioVenta()) : "N/A");
            ((Label) componentes[8]).setText(vehiculo.getKilometraje() != null ?
                    String.format("%,.0f km", vehiculo.getKilometraje()) : "N/A");
            ((Label) componentes[9]).setText(vehiculo.getTipoCombustible() != null ?
                    vehiculo.getTipoCombustible() : "N/A");
            ((Label) componentes[10]).setText(vehiculo.getTransmision() != null ?
                    vehiculo.getTransmision() : "N/A");
            ((Label) componentes[11]).setText(vehiculo.getTipoVehiculo() != null ?
                    vehiculo.getTipoVehiculo() : "N/A");

            ((TextArea) componentes[12]).setText(vehiculo.getDescripcion() != null ?
                    vehiculo.getDescripcion() : "Sin descripción disponible.");

        } catch (Exception e) {
            System.err.println("Error al mostrar detalles: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void actualizarEstadisticas(List<Vehiculo> vehiculos) {
        int total = vehiculos.size();
        int disponibles = 0;
        int vendidos = 0;
        double valorTotal = 0.0;

        for (Vehiculo v : vehiculos) {
            if ("Disponible".equalsIgnoreCase(v.getEstadoVehiculo())) {
                disponibles++;
            } else if ("Vendido".equalsIgnoreCase(v.getEstadoVehiculo())) {
                vendidos++;
            }

            if (v.getPrecioVenta() != null) {
                valorTotal += v.getPrecioVenta().doubleValue();
            }
        }

        VBox panelInferior = (VBox) ((BorderPane) stage.getScene().getRoot()).getBottom();
        HBox panelEstadisticas = (HBox) panelInferior.getChildren().get(0);
        Object[] labels = (Object[]) panelEstadisticas.getUserData();

        ((Label) labels[0]).setText("Total vehículos: " + total);
        ((Label) labels[1]).setText("Disponibles: " + disponibles);
        ((Label) labels[2]).setText("Vendidos: " + vendidos);
        ((Label) labels[3]).setText("Valor inventario: $" + String.format("%,.2f", valorTotal));
    }

    private void exportarAPDF() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Exportar a PDF");
        alert.setHeaderText(null);
        alert.setContentText("Función de exportación a PDF en desarrollo...");
        alert.showAndWait();
    }
}