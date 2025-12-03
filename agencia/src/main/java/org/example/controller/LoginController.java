package org.example.controller;

import org.example.database.DatabaseConnection;
import org.example.model.Empleado;

import java.sql.*;

public class LoginController {
//lol
    public Empleado autenticar(String usuario, String contrasena) {
        String sql = """
            SELECT e.* 
            FROM usuario u 
            INNER JOIN empleado e ON u.idEmpleado = e.idEmpleado 
            WHERE u.user = ? AND u.contraseña = ? AND e.estado = 'AC'
            """;

        try (Connection conexion = DatabaseConnection.getConnection();
             PreparedStatement declaracion = conexion.prepareStatement(sql)) {

            declaracion.setString(1, usuario);
            declaracion.setString(2, contrasena);

            ResultSet resultados = declaracion.executeQuery();

            if (resultados.next()) {
                return mapearEmpleado(resultados);
            }
        } catch (SQLException e) {
            System.err.println("Error en autenticación: " + e.getMessage());
        }
        return null;
    }

    private Empleado mapearEmpleado(ResultSet resultados) throws SQLException {
        Empleado empleado = new Empleado();
        empleado.setIdEmpleado(resultados.getString("idEmpleado"));
        empleado.setNombre(resultados.getString("nombre"));
        empleado.setApellidoPaterno(resultados.getString("apellidoPaterno"));
        empleado.setApellidoMaterno(resultados.getString("apellidoMaterno"));
        empleado.setPuesto(resultados.getString("puesto"));
        empleado.setTelefono(resultados.getString("telefono"));
        empleado.setEmail(resultados.getString("email"));
        empleado.setSalario(resultados.getBigDecimal("salario"));

        Date fecha = resultados.getDate("fechaContratacion");
        if (fecha != null) {
            empleado.setFechaContratacion(fecha.toLocalDate());
        }

        empleado.setEstado(resultados.getString("estado"));
        return empleado;
    }
}