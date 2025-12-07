package org.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static DatabaseConnection instancia;
    private static Connection conexion;
    private static final String URL = "jdbc:mysql://localhost:3306/carros";
    private static final String USUARIO = "ego.skz";
    private static final String CONTRASEÑA = "6dmg4pvz";

    private DatabaseConnection() {
    }

    public static synchronized DatabaseConnection getInstance() {
        if (instancia == null) {
            instancia = new DatabaseConnection();
        }
        return instancia;
    }

    public static Connection getConnection() {
        try {
            DatabaseConnection instance = getInstance();
            if (instance.conexion == null || instance.conexion.isClosed()) {
                instance.conexion = DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
                System.out.println("Conexión a MySQL establecida");
            }
            return instance.conexion;
        } catch (SQLException e) {
            System.err.println("Error al conectar a MySQL: " + e.getMessage());
            e.printStackTrace();
        }
        return conexion;
    }

    public static void closeConnection() {
        try {
            DatabaseConnection instance = getInstance();
            if (instance.conexion != null && !instance.conexion.isClosed()) {
                instance.conexion.close();
                System.out.println("🔌 Conexión a MySQL cerrada");
                conexion = null;
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar conexión: " + e.getMessage());
        }
    }
}