package org.example.dao;

import org.example.database.DatabaseConnection;
import org.example.model.Proveedor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProveedorDAOImpl implements ProveedorDAO {

    @Override
    public boolean registrarProveedor(Proveedor proveedor) {
        String sql = "INSERT INTO proveedor (idProveedor, nombre, telefono, direccion, rfc, estado) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, proveedor.getIdProveedor());
            stmt.setString(2, proveedor.getNombre());
            stmt.setString(3, proveedor.getTelefono());
            stmt.setString(4, proveedor.getDireccion());
            stmt.setString(5, proveedor.getRfc());
            stmt.setString(6, proveedor.getEstado());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al registrar proveedor: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Proveedor buscarPorId(String idProveedor) {
        String sql = "SELECT * FROM proveedor WHERE idProveedor = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, idProveedor);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapearProveedor(rs);
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar proveedor: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Proveedor> listarTodos() {
        List<Proveedor> proveedores = new ArrayList<>();
        String sql = "SELECT * FROM proveedor ORDER BY nombre";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                proveedores.add(mapearProveedor(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error al listar proveedores: " + e.getMessage());
            e.printStackTrace();
        }
        return proveedores;
    }

    @Override
    public boolean actualizarProveedor(Proveedor proveedor) {
        String sql = "UPDATE proveedor SET nombre = ?, telefono = ?, direccion = ?, rfc = ?, estado = ? WHERE idProveedor = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, proveedor.getNombre());
            stmt.setString(2, proveedor.getTelefono());
            stmt.setString(3, proveedor.getDireccion());
            stmt.setString(4, proveedor.getRfc());
            stmt.setString(5, proveedor.getEstado());
            stmt.setString(6, proveedor.getIdProveedor());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar proveedor: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminarProveedor(String idProveedor) {
        String sql = "DELETE FROM proveedor WHERE idProveedor = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, idProveedor);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar proveedor: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private Proveedor mapearProveedor(ResultSet rs) throws SQLException {
        Proveedor proveedor = new Proveedor();
        proveedor.setIdProveedor(rs.getString("idProveedor"));
        proveedor.setNombre(rs.getString("nombre"));
        proveedor.setTelefono(rs.getString("telefono"));
        proveedor.setDireccion(rs.getString("direccion"));
        proveedor.setRfc(rs.getString("rfc"));
        proveedor.setEstado(rs.getString("estado"));
        return proveedor;
    }
}