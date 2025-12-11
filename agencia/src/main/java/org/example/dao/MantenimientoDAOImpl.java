package org.example.dao;

import org.example.database.DatabaseConnection;
import org.example.model.Mantenimiento;
import java.sql.*;
import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MantenimientoDAOImpl implements MantenimientoDAO {

    @Override
    public boolean registrarMantenimiento(Mantenimiento mantenimiento) {
        String sql = """
            INSERT INTO mantenimiento (
                idMantenimiento, idVehiculo, idEmpleado,
                tipoMantenimiento, descripcion, kilometrajeMomento,
                fechaMantenimiento, costo, proximoMantenimiento, estado
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, mantenimiento.getIdMantenimiento());
            stmt.setString(2, mantenimiento.getIdVehiculo());
            stmt.setString(3, mantenimiento.getRealizadoPor());
            stmt.setString(4, mantenimiento.getTipoMantenimiento());
            stmt.setString(5, mantenimiento.getDescripcion());

            if (mantenimiento.getKilometrajeMomento() != null) {
                stmt.setBigDecimal(6, mantenimiento.getKilometrajeMomento());
            } else {
                stmt.setNull(6, Types.DECIMAL);
            }

            stmt.setDate(7, Date.valueOf(mantenimiento.getFechaMantenimiento()));

            if (mantenimiento.getCosto() != null) {
                stmt.setBigDecimal(8, mantenimiento.getCosto());
            } else {
                stmt.setNull(8, Types.DECIMAL);
            }

            if (mantenimiento.getProximoMantenimiento() != null) {
                stmt.setDate(9, Date.valueOf(mantenimiento.getProximoMantenimiento()));
            } else {
                stmt.setNull(9, Types.DATE);
            }

            stmt.setString(10, mantenimiento.getEstado());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al registrar mantenimiento: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Mantenimiento> listarPorVehiculo(String idVehiculo) {
        List<Mantenimiento> mantenimientos = new ArrayList<>();
        String sql = "SELECT * FROM mantenimiento WHERE idVehiculo = ? ORDER BY fechaMantenimiento DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, idVehiculo);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                mantenimientos.add(mapearMantenimiento(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error al listar mantenimientos: " + e.getMessage());
            e.printStackTrace();
        }
        return mantenimientos;
    }

    @Override
    public List<Mantenimiento> listarTodos() {
        List<Mantenimiento> mantenimientos = new ArrayList<>();
        String sql = "SELECT * FROM mantenimiento ORDER BY fechaMantenimiento DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                mantenimientos.add(mapearMantenimiento(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error al listar mantenimientos: " + e.getMessage());
            e.printStackTrace();
        }
        return mantenimientos;
    }

    @Override
    public boolean actualizarMantenimiento(Mantenimiento mantenimiento) {
        String sql = """
            UPDATE mantenimiento SET 
                idVehiculo = ?, idEmpleado = ?, fechaMantenimiento = ?, 
                tipoMantenimiento = ?, descripcion = ?, kilometrajeMomento = ?,
                costo = ?, proximoMantenimiento = ?, estado = ?
            WHERE idMantenimiento = ?
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, mantenimiento.getIdVehiculo());
            stmt.setString(2, mantenimiento.getRealizadoPor());
            stmt.setDate(3, Date.valueOf(mantenimiento.getFechaMantenimiento()));
            stmt.setString(4, mantenimiento.getTipoMantenimiento());
            stmt.setString(5, mantenimiento.getDescripcion());

            if (mantenimiento.getKilometrajeMomento() != null) {
                stmt.setBigDecimal(6, mantenimiento.getKilometrajeMomento());
            } else {
                stmt.setNull(6, Types.DECIMAL);
            }

            if (mantenimiento.getCosto() != null) {
                stmt.setBigDecimal(7, mantenimiento.getCosto());
            } else {
                stmt.setNull(7, Types.DECIMAL);
            }

            if (mantenimiento.getProximoMantenimiento() != null) {
                stmt.setDate(8, Date.valueOf(mantenimiento.getProximoMantenimiento()));
            } else {
                stmt.setNull(8, Types.DATE);
            }

            stmt.setString(9, mantenimiento.getEstado());
            stmt.setString(10, mantenimiento.getIdMantenimiento());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar mantenimiento: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminarMantenimiento(String idMantenimiento) {
        String sql = "DELETE FROM mantenimiento WHERE idMantenimiento = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, idMantenimiento);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar mantenimiento: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Mantenimiento buscarPorId(String idMantenimiento) {
        String sql = "SELECT * FROM mantenimiento WHERE idMantenimiento = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, idMantenimiento);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapearMantenimiento(rs);
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar mantenimiento: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    private Mantenimiento mapearMantenimiento(ResultSet rs) throws SQLException {
        Mantenimiento mantenimiento = new Mantenimiento();

        mantenimiento.setIdMantenimiento(rs.getString("idMantenimiento"));
        mantenimiento.setIdVehiculo(rs.getString("idVehiculo"));
        mantenimiento.setRealizadoPor(rs.getString("idEmpleado"));

        Date fecha = rs.getDate("fechaMantenimiento");
        if (fecha != null) {
            mantenimiento.setFechaMantenimiento(fecha.toLocalDate());
        }

        mantenimiento.setTipoMantenimiento(rs.getString("tipoMantenimiento"));
        mantenimiento.setDescripcion(rs.getString("descripcion"));

        BigDecimal kilometraje = rs.getBigDecimal("kilometrajeMomento");
        if (!rs.wasNull()) {
            mantenimiento.setKilometrajeMomento(kilometraje);
        }

        BigDecimal costo = rs.getBigDecimal("costo");
        if (!rs.wasNull()) {
            mantenimiento.setCosto(costo);
        }

        Date proximo = rs.getDate("proximoMantenimiento");
        if (proximo != null) {
            mantenimiento.setProximoMantenimiento(proximo.toLocalDate());
        }

        mantenimiento.setEstado(rs.getString("estado"));

        return mantenimiento;
    }
}