package org.example.dao;

import org.example.database.DatabaseConnection;
import org.example.model.Vehiculo;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VehiculoDAOImpl implements VehiculoDAO {

    @Override
    public boolean registrarVehiculo(Vehiculo vehiculo) {
        String sql = """
            INSERT INTO vehiculo (
                idVehiculo, nombre, modelo, año, marca, paisOrigen, 
                kilometraje, estadoVehiculo, noSerie, color, precioCompra, 
                precioVenta, fechaVenta, fechaIngreso, descripcion, 
                tipoCombustible, capacidad, tipoVehiculo, transmision, foto, idProveedor
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, vehiculo.getIdVehiculo());
            stmt.setString(2, vehiculo.getNombre());
            stmt.setString(3, vehiculo.getModelo());
            stmt.setInt(4, vehiculo.getAño());
            stmt.setString(5, vehiculo.getMarca());
            stmt.setString(6, vehiculo.getPaisOrigen());
            stmt.setBigDecimal(7, vehiculo.getKilometraje());
            stmt.setString(8, vehiculo.getEstadoVehiculo());
            stmt.setString(9, vehiculo.getNoSerie());
            stmt.setString(10, vehiculo.getColor());
            stmt.setBigDecimal(11, vehiculo.getPrecioCompra());
            stmt.setBigDecimal(12, vehiculo.getPrecioVenta());

            if (vehiculo.getFechaVenta() != null) {
                stmt.setDate(13, Date.valueOf(vehiculo.getFechaVenta()));
            } else {
                stmt.setNull(13, Types.DATE);
            }

            if (vehiculo.getFechaIngreso() != null) {
                stmt.setDate(14, Date.valueOf(vehiculo.getFechaIngreso()));
            } else {
                stmt.setDate(14, Date.valueOf(LocalDate.now()));
            }

            stmt.setString(15, vehiculo.getDescripcion());
            stmt.setString(16, vehiculo.getTipoCombustible());
            stmt.setString(17, vehiculo.getCapacidad());
            stmt.setString(18, vehiculo.getTipoVehiculo());
            stmt.setString(19, vehiculo.getTransmision());

            if (vehiculo.getFoto() != null) {
                stmt.setBlob(20, vehiculo.getFoto());
            } else {
                stmt.setNull(20, Types.BLOB);
            }

            stmt.setString(21, vehiculo.getIdProveedor());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al registrar vehículo: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
//LOL
    @Override
    public Vehiculo buscarPorId(String idVehiculo) {
        String sql = "SELECT * FROM vehiculo WHERE idVehiculo = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, idVehiculo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapearVehiculo(rs);
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar vehículo: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Vehiculo> listarTodos() {
        List<Vehiculo> vehiculos = new ArrayList<>();
        String sql = "SELECT * FROM vehiculo ORDER BY fechaIngreso DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                vehiculos.add(mapearVehiculo(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error al listar vehículos: " + e.getMessage());
            e.printStackTrace();
        }
        return vehiculos;
    }

    @Override
    public boolean actualizarVehiculo(Vehiculo vehiculo) {
        String sql = """
            UPDATE vehiculo SET 
                nombre = ?, modelo = ?, año = ?, marca = ?, paisOrigen = ?,
                kilometraje = ?, estadoVehiculo = ?, noSerie = ?, color = ?,
                precioCompra = ?, precioVenta = ?, fechaVenta = ?, fechaIngreso = ?,
                descripcion = ?, tipoCombustible = ?, capacidad = ?, tipoVehiculo = ?,
                transmision = ?, foto = ?, idProveedor = ?
            WHERE idVehiculo = ?
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, vehiculo.getNombre());
            stmt.setString(2, vehiculo.getModelo());
            stmt.setInt(3, vehiculo.getAño());
            stmt.setString(4, vehiculo.getMarca());
            stmt.setString(5, vehiculo.getPaisOrigen());
            stmt.setBigDecimal(6, vehiculo.getKilometraje());
            stmt.setString(7, vehiculo.getEstadoVehiculo());
            stmt.setString(8, vehiculo.getNoSerie());
            stmt.setString(9, vehiculo.getColor());
            stmt.setBigDecimal(10, vehiculo.getPrecioCompra());
            stmt.setBigDecimal(11, vehiculo.getPrecioVenta());

            if (vehiculo.getFechaVenta() != null) {
                stmt.setDate(12, Date.valueOf(vehiculo.getFechaVenta()));
            } else {
                stmt.setNull(12, Types.DATE);
            }

            if (vehiculo.getFechaIngreso() != null) {
                stmt.setDate(13, Date.valueOf(vehiculo.getFechaIngreso()));
            } else {
                stmt.setNull(13, Types.DATE);
            }

            stmt.setString(14, vehiculo.getDescripcion());
            stmt.setString(15, vehiculo.getTipoCombustible());
            stmt.setString(16, vehiculo.getCapacidad());
            stmt.setString(17, vehiculo.getTipoVehiculo());
            stmt.setString(18, vehiculo.getTransmision());

            if (vehiculo.getFoto() != null) {
                stmt.setBlob(19, vehiculo.getFoto());
            } else {
                stmt.setNull(19, Types.BLOB);
            }

            stmt.setString(20, vehiculo.getIdProveedor());
            stmt.setString(21, vehiculo.getIdVehiculo());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar vehículo: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminarVehiculo(String idVehiculo) {
        String sql = "DELETE FROM vehiculo WHERE idVehiculo = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, idVehiculo);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar vehículo: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private Vehiculo mapearVehiculo(ResultSet rs) throws SQLException {
        Vehiculo vehiculo = new Vehiculo();

        vehiculo.setIdVehiculo(rs.getString("idVehiculo"));
        vehiculo.setNombre(rs.getString("nombre"));
        vehiculo.setModelo(rs.getString("modelo"));
        vehiculo.setAño(rs.getInt("año"));
        vehiculo.setMarca(rs.getString("marca"));
        vehiculo.setPaisOrigen(rs.getString("paisOrigen"));
        vehiculo.setKilometraje(rs.getBigDecimal("kilometraje"));
        vehiculo.setEstadoVehiculo(rs.getString("estadoVehiculo"));
        vehiculo.setNoSerie(rs.getString("noSerie"));
        vehiculo.setColor(rs.getString("color"));
        vehiculo.setPrecioCompra(rs.getBigDecimal("precioCompra"));
        vehiculo.setPrecioVenta(rs.getBigDecimal("precioVenta"));

        Date fechaVenta = rs.getDate("fechaVenta");
        if (fechaVenta != null) {
            vehiculo.setFechaVenta(fechaVenta.toLocalDate());
        }

        Date fechaIngreso = rs.getDate("fechaIngreso");
        if (fechaIngreso != null) {
            vehiculo.setFechaIngreso(fechaIngreso.toLocalDate());
        }

        vehiculo.setDescripcion(rs.getString("descripcion"));
        vehiculo.setTipoCombustible(rs.getString("tipoCombustible"));
        vehiculo.setCapacidad(rs.getString("capacidad"));
        vehiculo.setTipoVehiculo(rs.getString("tipoVehiculo"));
        vehiculo.setTransmision(rs.getString("transmision"));
        vehiculo.setFoto(rs.getBlob("foto"));
        vehiculo.setIdProveedor(rs.getString("idProveedor"));

        return vehiculo;
    }
}