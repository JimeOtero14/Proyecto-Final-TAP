package org.example;
//lol
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.example.database.DatabaseConnection;
import org.example.view.LoginView;

public class Main extends Application {

    @Override
    public void start(Stage ventanaPrincipal) {
        if (!verificarMySQLDriver()) {
            return;
        }

        try {
            LoginView vistaLogin = new LoginView();
            Scene escena = new Scene(vistaLogin.getRoot(), 600, 400);

            ventanaPrincipal.setTitle("Agencia de Autos - Login");
            ventanaPrincipal.setScene(escena);
            ventanaPrincipal.setResizable(false);

            ventanaPrincipal.setOnHidden(e -> {
                DatabaseConnection.getInstance().closeConnection();
                org.example.database.DatabaseConnection.closeConnection();
            });

            ventanaPrincipal.show();

        } catch (Exception e) {
            System.err.println("Error al crear la interfaz: " + e.getMessage());
            e.printStackTrace();
            mostrarAlertaError("Error de aplicación",
                    "No se pudo iniciar la interfaz gráfica: " + e.getMessage());
        }
    }

    private boolean verificarMySQLDriver() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver MySQL cargado correctamente");
            return true;
        } catch (ClassNotFoundException e) {
            System.err.println("Error: MySQL Driver no encontrado");
            return false;
        }
    }

    private void mostrarAlertaError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        System.out.println("Iniciando Agencia de Autos...");
        System.out.println("Java Version: " + System.getProperty("java.version"));
        launch(args);

        try {
            launch(args);
        } catch (Exception e) {
            System.err.println("Error fatal: " + e.getMessage());
            e.printStackTrace();
        }
    }
}