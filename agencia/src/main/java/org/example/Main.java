package org.example;
//lol
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage ventanaPrincipal) {
        if (!verificarMySQLDriver()) {
            return;
        }

        try {
            org.example.view.LoginView vistaLogin = new org.example.view.LoginView();
            Scene escena = new Scene(vistaLogin.getRoot(), 600, 400);

            ventanaPrincipal.setTitle("Agencia de Autos - Login");
            ventanaPrincipal.setScene(escena);
            ventanaPrincipal.setResizable(false);

            ventanaPrincipal.setOnHidden(e -> {
                org.example.database.DatabaseConnection.closeConnection();
            });

            ventanaPrincipal.show();

        } catch (Exception e) {
            System.err.println("Error al crear la interfaz: " + e.getMessage());
            e.printStackTrace();
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

    public static void main(String[] args) {
        System.out.println("Iniciando Agencia de Autos...");
        System.out.println("Java Version: " + System.getProperty("java.version"));
        launch(args);
    }
}