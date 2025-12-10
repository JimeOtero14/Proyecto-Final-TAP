package org.example.view;

import org.example.controller.RegistroVehiculoController;
import org.example.model.Empleado;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.io.File;
import java.sql.Blob;

public class RegistroVehiculoView {
    private Stage stage;
    private RegistroVehiculoController controller;
    private TabPane tabPane;
    private File archivoFotoSeleccionado;
    private Image imagenPrevia;
    private ImageView visorImagen;

    public RegistroVehiculoView(Stage stage, Empleado mecanico) {
        this.stage = stage;
        this.controller = new RegistroVehiculoController(mecanico);
        crearVista();
    }

    private void crearVista() {
        tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Tab tabBasica = new Tab("Información Básica");
        tabBasica.setContent(crearPanelInformacionBasica());

        Tab tabTecnica = new Tab("Especificaciones");
        tabTecnica.setContent(crearPanelEspecificaciones());

        Tab tabFinanzas = new Tab("Precios y Fechas");
        tabFinanzas.setContent(crearPanelFinanzas());

        tabPane.getTabs().addAll(tabBasica, tabTecnica, tabFinanzas);

        BorderPane panelPrincipal = new BorderPane();
        panelPrincipal.setCenter(tabPane);
        panelPrincipal.setBottom(crearPanelBotones());

        Scene scene = new Scene(panelPrincipal, 700, 550);
        stage.setScene(scene);
        stage.setTitle("Registro de Vehículo");
        stage.setResizable(false);

        try {
            scene.getStylesheets().add(
                    getClass().getResource("/styles/estilos.css").toExternalForm()
            );
        } catch (Exception e) {
            System.err.println("No se pudo cargar CSS: " + e.getMessage());
        }
    }

    private GridPane crearPanelInformacionBasica() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(15));
        grid.setHgap(10);
        grid.setVgap(10);

        int fila = 0;

        grid.add(new Label("ID Vehículo*:"), 0, fila);
        TextField campoIdVehiculo = new TextField();
        campoIdVehiculo.setId("idVehiculo");
        campoIdVehiculo.setPrefWidth(180);
        grid.add(campoIdVehiculo, 1, fila);

        grid.add(new Label("No. Serie:"), 2, fila);
        TextField campoNoSerie = new TextField();
        campoNoSerie.setId("noSerie");
        campoNoSerie.setPrefWidth(180);
        grid.add(campoNoSerie, 3, fila);
        fila++;

        grid.add(new Label("Marca*:"), 0, fila);
        TextField campoMarca = new TextField();
        campoMarca.setId("marca");
        campoMarca.setPrefWidth(180);
        grid.add(campoMarca, 1, fila);

        grid.add(new Label("Modelo*:"), 2, fila);
        TextField campoModelo = new TextField();
        campoModelo.setId("modelo");
        campoModelo.setPrefWidth(180);
        grid.add(campoModelo, 3, fila);
        fila++;

        grid.add(new Label("Año*:"), 0, fila);
        Spinner<Integer> spinnerAño = new Spinner<>(1900, LocalDate.now().getYear() + 1, LocalDate.now().getYear());
        spinnerAño.setEditable(true);
        spinnerAño.setPrefWidth(180);
        spinnerAño.setId("año");
        grid.add(spinnerAño, 1, fila);

        grid.add(new Label("Color:"), 2, fila);
        TextField campoColor = new TextField();
        campoColor.setId("color");
        campoColor.setPrefWidth(180);
        grid.add(campoColor, 3, fila);
        fila++;

        grid.add(new Label("Nombre:"), 0, fila);
        TextField campoNombre = new TextField();
        campoNombre.setId("nombre");
        campoNombre.setPrefWidth(180);
        grid.add(campoNombre, 1, fila);

        grid.add(new Label("País Origen:"), 2, fila);
        TextField campoPaisOrigen = new TextField();
        campoPaisOrigen.setId("paisOrigen");
        campoPaisOrigen.setPrefWidth(180);
        grid.add(campoPaisOrigen, 3, fila);
        fila++;

        grid.add(new Label("ID Proveedor:"), 0, fila);
        TextField campoIdProveedor = new TextField();
        campoIdProveedor.setId("idProveedor");
        campoIdProveedor.setPrefWidth(180);
        grid.add(campoIdProveedor, 1, fila);

        grid.add(new Label("Estado*:"), 2, fila);
        ComboBox<String> comboEstado = new ComboBox<>();
        comboEstado.getItems().addAll("Disponible", "Vendido", "Reservado", "Mantenimiento");
        comboEstado.setValue("Disponible");
        comboEstado.setPrefWidth(180);
        comboEstado.setId("estado");
        grid.add(comboEstado, 3, fila);

        return grid;
    }

    private GridPane crearPanelEspecificaciones() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(15));
        grid.setHgap(10);
        grid.setVgap(10);

        int fila = 0;

        grid.add(new Label("Tipo Combustible:"), 0, fila);
        ComboBox<String> comboTipoCombustible = new ComboBox<>();
        comboTipoCombustible.getItems().addAll("Gasolina", "Diésel", "Eléctrico", "Híbrido", "Gas");
        comboTipoCombustible.setPrefWidth(180);
        comboTipoCombustible.setId("tipoCombustible");
        grid.add(comboTipoCombustible, 1, fila);

        grid.add(new Label("Tipo Vehículo:"), 2, fila);
        ComboBox<String> comboTipoVehiculo = new ComboBox<>();
        comboTipoVehiculo.getItems().addAll("Sedán", "SUV", "Pickup", "Deportivo", "Van", "Motocicleta");
        comboTipoVehiculo.setPrefWidth(180);
        comboTipoVehiculo.setId("tipoVehiculo");
        grid.add(comboTipoVehiculo, 3, fila);
        fila++;

        grid.add(new Label("Transmisión:"), 0, fila);
        ComboBox<String> comboTransmision = new ComboBox<>();
        comboTransmision.getItems().addAll("Manual", "Automática", "CVT", "Semi-automática");
        comboTransmision.setPrefWidth(180);
        comboTransmision.setId("transmision");
        grid.add(comboTransmision, 1, fila);

        grid.add(new Label("Capacidad:"), 2, fila);
        ComboBox<String> comboCapacidad = new ComboBox<>();
        comboCapacidad.getItems().addAll("2", "4", "5", "7", "8+");
        comboCapacidad.setPrefWidth(180);
        comboCapacidad.setId("capacidad");
        grid.add(comboCapacidad, 3, fila);
        fila++;

        grid.add(new Label("Kilometraje:"), 0, fila);
        TextField campoKilometraje = new TextField();
        campoKilometraje.setPromptText("0.00");
        campoKilometraje.setId("kilometraje");
        campoKilometraje.setPrefWidth(180);
        campoKilometraje.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("\\d*(\\.\\d*)?")) {
                campoKilometraje.setText(oldVal);
            }
        });
        grid.add(campoKilometraje, 1, fila);
        fila++;

        grid.add(new Label("Descripción:"), 0, fila);
        TextArea areaDescripcion = new TextArea();
        areaDescripcion.setPrefRowCount(4);
        areaDescripcion.setPrefWidth(480);
        areaDescripcion.setWrapText(true);
        areaDescripcion.setId("descripcion");
        GridPane.setColumnSpan(areaDescripcion, 3);
        GridPane.setRowSpan(areaDescripcion, 2);
        grid.add(areaDescripcion, 1, fila);

        return grid;
    }

    private GridPane crearPanelFinanzas() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(15));
        grid.setHgap(10);
        grid.setVgap(10);

        int fila = 0;

        grid.add(new Label("Precio Compra ($):"), 0, fila);
        TextField campoPrecioCompra = new TextField();
        campoPrecioCompra.setPromptText("0.00");
        campoPrecioCompra.setId("precioCompra");
        campoPrecioCompra.setPrefWidth(180);
        campoPrecioCompra.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("\\d*(\\.\\d*)?")) {
                campoPrecioCompra.setText(oldVal);
            }
        });
        grid.add(campoPrecioCompra, 1, fila);

        grid.add(new Label("Precio Venta ($):"), 2, fila);
        TextField campoPrecioVenta = new TextField();
        campoPrecioVenta.setPromptText("0.00");
        campoPrecioVenta.setId("precioVenta");
        campoPrecioVenta.setPrefWidth(180);
        campoPrecioVenta.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("\\d*(\\.\\d*)?")) {
                campoPrecioVenta.setText(oldVal);
            }
        });
        grid.add(campoPrecioVenta, 3, fila);
        fila++;

        grid.add(new Label("Fecha Ingreso*:"), 0, fila);
        DatePicker selectorFechaIngreso = new DatePicker();
        selectorFechaIngreso.setValue(LocalDate.now());
        selectorFechaIngreso.setPrefWidth(180);
        selectorFechaIngreso.setId("fechaIngreso");
        grid.add(selectorFechaIngreso, 1, fila);

        grid.add(new Label("Fecha Venta:"), 2, fila);
        DatePicker selectorFechaVenta = new DatePicker();
        selectorFechaVenta.setPrefWidth(180);
        selectorFechaVenta.setId("fechaVenta");
        grid.add(selectorFechaVenta, 3, fila);
        fila++;

        grid.add(new Label("Foto:"), 0, fila);

        HBox panelFoto = new HBox(10);
        panelFoto.setAlignment(Pos.CENTER_LEFT);

        Button botonSeleccionarFoto = new Button("Seleccionar");
        botonSeleccionarFoto.setOnAction(e -> seleccionarFoto());

        Button botonQuitarFoto = new Button("Quitar");
        botonQuitarFoto.setOnAction(e -> quitarFoto());

        visorImagen = new ImageView();
        visorImagen.setFitWidth(100);
        visorImagen.setFitHeight(80);
        visorImagen.setPreserveRatio(true);
        visorImagen.setStyle("-fx-border-color: lightgray; -fx-border-width: 1px;");

        panelFoto.getChildren().addAll(botonSeleccionarFoto, botonQuitarFoto, visorImagen);
        grid.add(panelFoto, 1, fila, 3, 1);

        return grid;
    }

    private HBox crearPanelBotones() {
        HBox panelBotones = new HBox(20);
        panelBotones.setPadding(new Insets(15));
        panelBotones.setAlignment(Pos.CENTER);

        Button botonRegistrar = new Button("Registrar Vehículo");
        botonRegistrar.setStyle("-fx-font-weight: bold; -fx-padding: 10 25;");
        botonRegistrar.setOnAction(e -> procesarRegistro());

        Button botonCancelar = new Button("Cancelar");
        botonCancelar.setStyle("-fx-padding: 10 25;");
        botonCancelar.setOnAction(e -> controller.volverAMecanicoView(stage));

        Button botonLimpiar = new Button("Limpiar Formulario");
        botonLimpiar.setStyle("-fx-padding: 10 25;");
        botonLimpiar.setOnAction(e -> limpiarFormulario());

        panelBotones.getChildren().addAll(botonRegistrar, botonLimpiar, botonCancelar);

        return panelBotones;
    }

    private void seleccionarFoto() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Imagen del Vehículo");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp"),
                new FileChooser.ExtensionFilter("Todos los archivos", "*.*")
        );

        Stage stageActual = (Stage) tabPane.getScene().getWindow();
        archivoFotoSeleccionado = fileChooser.showOpenDialog(stageActual);

        if (archivoFotoSeleccionado != null) {
            try {
                imagenPrevia = new Image(archivoFotoSeleccionado.toURI().toString());
                visorImagen.setImage(imagenPrevia);
            } catch (Exception e) {
                mostrarError("Error al cargar la imagen: " + e.getMessage());
            }
        }
    }

    private void quitarFoto() {
        archivoFotoSeleccionado = null;
        imagenPrevia = null;
        visorImagen.setImage(null);
    }

    private void procesarRegistro() {
        try {
            TextField campoIdVehiculo = (TextField) tabPane.lookup("#idVehiculo");
            TextField campoMarca = (TextField) tabPane.lookup("#marca");
            TextField campoModelo = (TextField) tabPane.lookup("#modelo");
            Spinner<Integer> spinnerAño = (Spinner<Integer>) tabPane.lookup("#año");
            ComboBox<String> comboEstado = (ComboBox<String>) tabPane.lookup("#estado");

            if (campoIdVehiculo.getText().trim().isEmpty()) {
                mostrarError("ID Vehículo es obligatorio");
                campoIdVehiculo.requestFocus();
                return;
            }

            if (campoMarca.getText().trim().isEmpty()) {
                mostrarError("Marca es obligatoria");
                campoMarca.requestFocus();
                return;
            }

            if (campoModelo.getText().trim().isEmpty()) {
                mostrarError("Modelo es obligatorio");
                campoModelo.requestFocus();
                return;
            }

            TextField campoNombre = (TextField) tabPane.lookup("#nombre");
            TextField campoPaisOrigen = (TextField) tabPane.lookup("#paisOrigen");
            TextField campoNoSerie = (TextField) tabPane.lookup("#noSerie");
            TextField campoColor = (TextField) tabPane.lookup("#color");
            TextField campoKilometraje = (TextField) tabPane.lookup("#kilometraje");
            TextField campoPrecioCompra = (TextField) tabPane.lookup("#precioCompra");
            TextField campoPrecioVenta = (TextField) tabPane.lookup("#precioVenta");
            TextField campoIdProveedor = (TextField) tabPane.lookup("#idProveedor");

            DatePicker selectorFechaVenta = (DatePicker) tabPane.lookup("#fechaVenta");
            DatePicker selectorFechaIngreso = (DatePicker) tabPane.lookup("#fechaIngreso");

            TextArea areaDescripcion = (TextArea) tabPane.lookup("#descripcion");
            ComboBox<String> comboTipoCombustible = (ComboBox<String>) tabPane.lookup("#tipoCombustible");
            ComboBox<String> comboCapacidad = (ComboBox<String>) tabPane.lookup("#capacidad");
            ComboBox<String> comboTipoVehiculo = (ComboBox<String>) tabPane.lookup("#tipoVehiculo");
            ComboBox<String> comboTransmision = (ComboBox<String>) tabPane.lookup("#transmision");

            BigDecimal kilometraje = campoKilometraje.getText().isEmpty() ?
                    BigDecimal.ZERO : new BigDecimal(campoKilometraje.getText());

            BigDecimal precioCompra = campoPrecioCompra.getText().isEmpty() ?
                    null : new BigDecimal(campoPrecioCompra.getText());

            BigDecimal precioVenta = campoPrecioVenta.getText().isEmpty() ?
                    null : new BigDecimal(campoPrecioVenta.getText());

            Blob fotoBlob = null;
            if (archivoFotoSeleccionado != null) {
                try {
                    byte[] bytes = java.nio.file.Files.readAllBytes(archivoFotoSeleccionado.toPath());
                    fotoBlob = new javax.sql.rowset.serial.SerialBlob(bytes);
                } catch (Exception e) {
                    mostrarError("Error al procesar la foto: " + e.getMessage());
                    return;
                }
            }

            boolean exito = controller.registrarVehiculo(
                    campoIdVehiculo.getText().trim(),
                    campoNombre.getText().trim(),
                    campoModelo.getText().trim(),
                    spinnerAño.getValue(),
                    campoMarca.getText().trim(),
                    campoPaisOrigen.getText().trim(),
                    kilometraje,
                    comboEstado.getValue(),
                    campoNoSerie.getText().trim(),
                    campoColor.getText().trim(),
                    precioCompra,
                    precioVenta,
                    selectorFechaVenta.getValue(),
                    selectorFechaIngreso.getValue(),
                    areaDescripcion.getText().trim(),
                    comboTipoCombustible.getValue(),
                    comboCapacidad.getValue(),
                    comboTipoVehiculo.getValue(),
                    comboTransmision.getValue(),
                    fotoBlob,
                    campoIdProveedor.getText().trim()
            );

            if (exito) {
                controller.volverAMecanicoView(stage);
            }

        } catch (NumberFormatException e) {
            mostrarError("Formato numérico inválido. Use números con punto decimal");
        } catch (Exception e) {
            mostrarError("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void limpiarFormulario() {
        limpiarCamposEnTab(0);
        limpiarCamposEnTab(1);
        limpiarCamposEnTab(2);
    }

    private void limpiarCamposEnTab(int indiceTab) {
        Tab tab = tabPane.getTabs().get(indiceTab);
        GridPane grid = (GridPane) tab.getContent();

        for (javafx.scene.Node node : grid.getChildren()) {
            if (node instanceof TextField) {
                ((TextField) node).clear();
            } else if (node instanceof TextArea) {
                ((TextArea) node).clear();
            } else if (node instanceof ComboBox) {
                ((ComboBox<?>) node).setValue(null);
            } else if (node instanceof Spinner) {
                @SuppressWarnings("unchecked")
                Spinner<Integer> spinner = (Spinner<Integer>) node;
                spinner.getValueFactory().setValue(LocalDate.now().getYear());
            } else if (node instanceof DatePicker) {
                DatePicker datePicker = (DatePicker) node;
                if (datePicker.getId() != null && datePicker.getId().equals("fechaIngreso")) {
                    datePicker.setValue(LocalDate.now());
                } else {
                    datePicker.setValue(null);
                }
            }
        }

        if (indiceTab == 2) {
            archivoFotoSeleccionado = null;
            imagenPrevia = null;
            visorImagen.setImage(null);
        }
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error de Validación");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.initOwner(stage);
        alert.showAndWait();
    }
}