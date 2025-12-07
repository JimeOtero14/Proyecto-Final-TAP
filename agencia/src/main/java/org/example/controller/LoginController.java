package org.example.controller;

import org.example.database.DatabaseConnection;
import org.example.model.Empleado;
import org.example.util.Constantes;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;

public class LoginController {

    public Empleado autenticar(String usuario, String contrasena) {
        if (usuario == null || usuario.trim().isEmpty()) {
            System.err.println("Error: Usuario no puede ser nulo o vacío");
            return null;
        }

        if (contrasena == null || contrasena.trim().isEmpty()) {
            System.err.println("Error: Contraseña no puede ser nula o vacía");
            return null;
        }

        if (usuario.length() < 3) {
            System.err.println("Error: Usuario demasiado corto");
            return null;
        }

        if (contrasena.length() < 4) {
            System.err.println("Error: Contraseña demasiado corta");
            return null;
        }

        String sql = """
            SELECT e.* 
            FROM usuario u 
            INNER JOIN empleado e ON u.idEmpleado = e.idEmpleado 
            WHERE u.username = ? AND u.contraseña = ? AND e.estado = ?
            """;

        Connection conexion = null;
        PreparedStatement declaracion = null;
        ResultSet resultados = null;

        try {
            conexion = DatabaseConnection.getInstance().getConnection();
            if (conexion == null || conexion.isClosed()) {
                System.err.println("Error: No hay conexión a la base de datos");
                return null;
            }

            declaracion = conexion.prepareStatement(sql);
            declaracion.setString(1, usuario);
            declaracion.setString(2, contrasena);
            declaracion.setString(3, Constantes.ESTADO_ACTIVO);

            resultados = declaracion.executeQuery();

            if (resultados.next()) {
                Empleado empleado = mapearEmpleado(resultados);
                System.out.println("Login exitoso para usuario: " + usuario);
                return empleado;
            } else {
                System.err.println("Login fallido para usuario: " + usuario);
                return null;
            }

        } catch (SQLException e) {
            System.err.println("Error SQL en autenticación: " + e.getMessage());
            e.printStackTrace();
            return null;
        } finally {
            cerrarRecursos(resultados, declaracion, null);
        }
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

        try {
            BigDecimal salario = resultados.getBigDecimal("salario");
            empleado.setSalario(salario);
        } catch (SQLException e) {
            System.err.println("Error al obtener salario: " + e.getMessage());
            empleado.setSalario(null);
        }

        Date fecha = resultados.getDate("fechaContratacion");
        if (fecha != null) {
            try {
                empleado.setFechaContratacion(fecha.toLocalDate());
            } catch (Exception e) {
                System.err.println("Error al convertir fecha: " + e.getMessage());
                empleado.setFechaContratacion(null);
            }
        } else {
            empleado.setFechaContratacion(null);
        }

        empleado.setEstado(resultados.getString("estado"));

        // Validar datos esenciales
        if (!validarEmpleado(empleado)) {
            throw new SQLException("Datos del empleado inválidos");
        }

        return empleado;
    }

    private boolean validarEmpleado(Empleado empleado) {
        if (empleado.getIdEmpleado() == null || empleado.getIdEmpleado().trim().isEmpty()) {
            return false;
        }

        if (empleado.getNombre() == null || empleado.getNombre().trim().isEmpty()) {
            return false;
        }

        if (empleado.getApellidoPaterno() == null || empleado.getApellidoPaterno().trim().isEmpty()) {
            return false;
        }

        if (empleado.getPuesto() == null || empleado.getPuesto().trim().isEmpty()) {
            return false;
        }

        return true;
    }

    private void cerrarRecursos(ResultSet rs, Statement stmt, Connection conn) {
        try {
            if (rs != null && !rs.isClosed()) {
                rs.close();
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar ResultSet: " + e.getMessage());
        }

        try {
            if (stmt != null && !stmt.isClosed()) {
                stmt.close();
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar Statement: " + e.getMessage());
        }

        // Nota: No cerramos la conexión aquí, se maneja en DatabaseConnection
    }

    // Método para verificar si un usuario existe (sin validar contraseña)
    public boolean verificarUsuarioExiste(String usuario) {
        if (usuario == null || usuario.trim().isEmpty()) {
            return false;
        }

        String sql = "SELECT COUNT(*) FROM usuario WHERE username = ?";

        try (Connection conexion = DatabaseConnection.getInstance().getConnection();
             PreparedStatement declaracion = conexion.prepareStatement(sql)) {

            declaracion.setString(1, usuario);
            ResultSet resultados = declaracion.executeQuery();

            if (resultados.next()) {
                return resultados.getInt(1) > 0;
            }

        } catch (SQLException e) {
            System.err.println("Error al verificar usuario: " + e.getMessage());
        }

        return false;
    }

    // Método para obtener información del usuario sin contraseña
    public Empleado obtenerInformacionUsuario(String idEmpleado) {
        if (idEmpleado == null || idEmpleado.trim().isEmpty()) {
            return null;
        }

        String sql = "SELECT * FROM empleado WHERE idEmpleado = ? AND estado = ?";

        try (Connection conexion = DatabaseConnection.getInstance().getConnection();
             PreparedStatement declaracion = conexion.prepareStatement(sql)) {

            declaracion.setString(1, idEmpleado);
            declaracion.setString(2, Constantes.ESTADO_ACTIVO);

            ResultSet resultados = declaracion.executeQuery();

            if (resultados.next()) {
                return mapearEmpleado(resultados);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener información del usuario: " + e.getMessage());
        }

        return null;
    }

    // Método para cambiar contraseña
    public boolean cambiarContrasena(String usuario, String contrasenaActual, String contrasenaNueva) {
        if (usuario == null || contrasenaActual == null || contrasenaNueva == null) {
            return false;
        }

        if (contrasenaNueva.length() < 4) {
            System.err.println("Error: Nueva contraseña demasiado corta");
            return false;
        }

        // Primero verificar que la contraseña actual es correcta
        Empleado empleado = autenticar(usuario, contrasenaActual);
        if (empleado == null) {
            System.err.println("Error: Contraseña actual incorrecta");
            return false;
        }

        String sql = "UPDATE usuario SET contraseña = ? WHERE username = ?";

        try (Connection conexion = DatabaseConnection.getInstance().getConnection();
             PreparedStatement declaracion = conexion.prepareStatement(sql)) {

            declaracion.setString(1, contrasenaNueva);
            declaracion.setString(2, usuario);

            int filasActualizadas = declaracion.executeUpdate();
            return filasActualizadas > 0;

        } catch (SQLException e) {
            System.err.println("Error al cambiar contraseña: " + e.getMessage());
            return false;
        }
    }
}