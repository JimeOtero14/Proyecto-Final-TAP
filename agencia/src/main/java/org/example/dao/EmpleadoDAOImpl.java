package org.example.dao;

import org.example.database.DatabaseConnection;
import org.example.model.Empleado;
import org.example.util.Constantes;
import java.sql.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAOImpl implements EmpleadoDAO {

    @Override
    public Empleado autenticar(String usuario, String contrasena) {
        System.out.println("=== DEBUG DAO ===");
        System.out.println("Usuario: " + usuario);
        System.out.println("Contraseña recibida: " + contrasena);

        String sql = "SELECT e.*, u.nivel FROM usuario u INNER JOIN empleado e ON u.idEmpleado = e.idEmpleado WHERE u.username = ? AND u.contraseña = ? AND e.estado = 'A'";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario);
            stmt.setString(2, contrasena);

            System.out.println("SQL: " + stmt.toString());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("USUARIO ENCONTRADO EN BD");
                return mapearEmpleado(rs);
            } else {
                System.out.println("NO SE ENCONTRÓ EN BD");
                String checkSql = "SELECT username, contraseña FROM usuario WHERE username = ?";
                PreparedStatement checkStmt = conn.prepareStatement(checkSql);
                checkStmt.setString(1, usuario);
                ResultSet checkRs = checkStmt.executeQuery();
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean registrarEmpleado(Empleado empleado) {
        String sql = """
            INSERT INTO empleado (
                idEmpleado, nombre, apellidoPaterno, apellidoMaterno, 
                puesto, telefono, email, salario, fechaContratacion, estado
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, empleado.getIdEmpleado());
            stmt.setString(2, empleado.getNombre());
            stmt.setString(3, empleado.getApellidoPaterno());
            stmt.setString(4, empleado.getApellidoMaterno());
            stmt.setString(5, empleado.getPuesto());
            stmt.setString(6, empleado.getTelefono());
            stmt.setString(7, empleado.getEmail());
            stmt.setBigDecimal(8, empleado.getSalario());
            stmt.setDate(9, Date.valueOf(empleado.getFechaContratacion()));
            stmt.setString(10, empleado.getEstado());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al registrar empleado: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Empleado buscarPorId(String idEmpleado) {
        String sql = "SELECT * FROM empleado WHERE idEmpleado = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, idEmpleado);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapearEmpleadoCompleto(rs);
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar empleado: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Empleado> listarTodos() {
        List<Empleado> empleados = new ArrayList<>();
        String sql = "SELECT * FROM empleado ORDER BY fechaContratacion DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                empleados.add(mapearEmpleadoCompleto(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error al listar empleados: " + e.getMessage());
            e.printStackTrace();
        }
        return empleados;
    }

    @Override
    public boolean actualizarEmpleado(Empleado empleado) {
        String sql = """
            UPDATE empleado SET 
                nombre = ?, apellidoPaterno = ?, apellidoMaterno = ?,
                puesto = ?, telefono = ?, email = ?, salario = ?,
                fechaContratacion = ?, estado = ?
            WHERE idEmpleado = ?
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, empleado.getNombre());
            stmt.setString(2, empleado.getApellidoPaterno());
            stmt.setString(3, empleado.getApellidoMaterno());
            stmt.setString(4, empleado.getPuesto());
            stmt.setString(5, empleado.getTelefono());
            stmt.setString(6, empleado.getEmail());
            stmt.setBigDecimal(7, empleado.getSalario());
            stmt.setDate(8, Date.valueOf(empleado.getFechaContratacion()));
            stmt.setString(9, empleado.getEstado());
            stmt.setString(10, empleado.getIdEmpleado());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar empleado: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminarEmpleado(String idEmpleado) {
        String sql = "DELETE FROM empleado WHERE idEmpleado = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, idEmpleado);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar empleado: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
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
        emp.setPuesto(rs.getString("nivel"));

        String nivel = emp.getPuesto();
        if (nivel == null || !nivel.matches("ADMIN|GERENTE|MECANICO|VENDEDOR|RH")) {
            throw new SQLException("Nivel de usuario inválido: " + nivel);
        }

        return emp;
    }

    private Empleado mapearEmpleadoCompleto(ResultSet rs) throws SQLException {
        Empleado emp = new Empleado();

        emp.setIdEmpleado(rs.getString("idEmpleado"));
        emp.setNombre(rs.getString("nombre"));
        emp.setApellidoPaterno(rs.getString("apellidoPaterno"));
        emp.setApellidoMaterno(rs.getString("apellidoMaterno"));
        emp.setPuesto(rs.getString("puesto"));
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

        return emp;
    }
    // En EmpleadoDAOImpl.java - Agregar al final
    @Override
    public List<Empleado> listarActivos() {
        List<Empleado> empleados = new ArrayList<>();
        String sql = "SELECT * FROM empleado WHERE estado = 'A' ORDER BY nombre";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                empleados.add(mapearEmpleadoCompleto(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error al listar empleados activos: " + e.getMessage());
            e.printStackTrace();
        }
        return empleados;
    }

    @Override
    public boolean cambiarEstadoEmpleado(String idEmpleado, String nuevoEstado) {
        String sql = "UPDATE empleado SET estado = ? WHERE idEmpleado = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nuevoEstado);
            stmt.setString(2, idEmpleado);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al cambiar estado del empleado: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}