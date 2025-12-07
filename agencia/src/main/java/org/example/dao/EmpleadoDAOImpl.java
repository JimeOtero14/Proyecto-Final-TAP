package org.example.dao;

import org.example.database.DatabaseConnection;
import org.example.model.Empleado;
import org.example.util.Constantes;
import java.sql.*;
import java.math.BigDecimal;

public class EmpleadoDAOImpl implements EmpleadoDAO {

    @Override
    public Empleado autenticar(String usuario, String contrasena) {
        String sql = """
            SELECT e.*, u.nivel 
            FROM usuario u 
            INNER JOIN empleado e ON u.idEmpleado = e.idEmpleado 
            WHERE u.username = ? AND u.contraseña = ? AND e.estado = ?
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario);
            stmt.setString(2, contrasena);
            stmt.setString(3, Constantes.ESTADO_ACTIVO);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapearEmpleado(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error en autenticación DAO: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    private Empleado mapearEmpleado(ResultSet rs) throws SQLException {
        Empleado emp = new Empleado();

        emp.setIdEmpleado(rs.getString("idEmpleado"));
        emp.setNombre(rs.getString("nombre"));
        emp.setApellidoPaterno(rs.getString("apellidoPaterno"));
        emp.setApellidoMaterno(rs.getString("apellidoMaterno"));
        emp.setTelefono(rs.getString("telefono"));
        emp.setEmail(rs.getString("email"));

        BigDecimal salario = rs.getBigDecimal("salario");
        if (!rs.wasNull()) {
            emp.setSalario(salario);
        }

        Date fecha = rs.getDate("fechaContratacion");
        if (fecha != null) {
            emp.setFechaContratacion(fecha.toLocalDate());
        }

        emp.setEstado(rs.getString("estado"));
        emp.setPuesto(rs.getString("nivel"));  // Nivel de la tabla usuario

        String nivel = emp.getPuesto();
        if (nivel == null || !nivel.matches("ADMIN|GERENTE|MECANICO|VENDEDOR")) {
            throw new SQLException("Nivel de usuario inválido: " + nivel);
        }

        return emp;
    }
}