package org.example.dao;

import org.example.database.DatabaseConnection;
import org.example.model.Documento;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DocumentoDAOImpl implements DocumentoDAO {

    @Override
    public boolean registrarDocumento(Documento documento) {
        String sql = "INSERT INTO documento (idVehiculo, idArchivo, nombre, archivo, tipoDocumento) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, documento.getIdVehiculo());
            stmt.setString(2, documento.getIdArchivo());
            stmt.setString(3, documento.getNombre());

            if (documento.getArchivo() != null) {
                stmt.setBlob(4, documento.getArchivo());
            } else {
                stmt.setNull(4, Types.BLOB);
            }

            stmt.setString(5, documento.getTipoDocumento());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al registrar documento: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Documento> listarPorVehiculo(String idVehiculo) {
        List<Documento> documentos = new ArrayList<>();
        String sql = "SELECT * FROM documento WHERE idVehiculo = ? ORDER BY nombre";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, idVehiculo);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                documentos.add(mapearDocumento(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error al listar documentos: " + e.getMessage());
            e.printStackTrace();
        }
        return documentos;
    }

    @Override
    public Documento buscarDocumento(String idVehiculo, String idArchivo) {
        String sql = "SELECT * FROM documento WHERE idVehiculo = ? AND idArchivo = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, idVehiculo);
            stmt.setString(2, idArchivo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapearDocumento(rs);
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar documento: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean eliminarDocumento(String idVehiculo, String idArchivo) {
        String sql = "DELETE FROM documento WHERE idVehiculo = ? AND idArchivo = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, idVehiculo);
            stmt.setString(2, idArchivo);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar documento: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Documento> listarTodosDocumentos() {
        List<Documento> documentos = new ArrayList<>();
        String sql = "SELECT * FROM documento ORDER BY idVehiculo, nombre";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                documentos.add(mapearDocumento(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error al listar documentos: " + e.getMessage());
            e.printStackTrace();
        }
        return documentos;
    }

    private Documento mapearDocumento(ResultSet rs) throws SQLException {
        Documento documento = new Documento();
        documento.setIdVehiculo(rs.getString("idVehiculo"));
        documento.setIdArchivo(rs.getString("idArchivo"));
        documento.setNombre(rs.getString("nombre"));
        documento.setArchivo(rs.getBlob("archivo"));
        documento.setTipoDocumento(rs.getString("tipoDocumento"));
        return documento;
    }
}