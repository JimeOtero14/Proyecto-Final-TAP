package org.example.dao;

import org.example.database.DatabaseConnection;
import org.example.model.Usuario;
import org.example.util.PasswordHasher;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAOImpl implements UsuarioDAO {

    @Override
    public boolean registrarUsuario(Usuario usuario) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            String sql = """
                INSERT INTO usuario (
                    idEmpleado, idUsuario, username, contraseña, fechaCreacion, nivel
                ) VALUES (?, ?, ?, ?, ?, ?)
                """;

            stmt = conn.prepareStatement(sql);

            String idUsuario = generarIdUsuario(conn);
            String passwordHash = PasswordHasher.hashPassword(usuario.getPassword());

            stmt.setString(1, usuario.getIdEmpleado());
            stmt.setString(2, idUsuario);
            stmt.setString(3, usuario.getUsername());
            stmt.setString(4, passwordHash);
            stmt.setDate(5, Date.valueOf(usuario.getFechaCreacion()));
            stmt.setString(6, usuario.getNivel());

            int resultado = stmt.executeUpdate();
            return resultado > 0;

        } catch (SQLException e) {
            System.err.println("Error al registrar usuario: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            cerrarRecursos(null, stmt, conn);
        }
    }

    @Override
    public Usuario buscarPorUsername(String username) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM usuario WHERE username = ?";

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return mapearUsuario(rs);
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar usuario: " + e.getMessage());
            e.printStackTrace();
        } finally {
            cerrarRecursos(rs, stmt, conn);
        }
        return null;
    }

    @Override
    public List<Usuario> listarTodos() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<Usuario> usuarios = new ArrayList<>();

        try {
            conn = DatabaseConnection.getConnection();
            String sql = """
                SELECT u.*, e.nombre, e.apellidoPaterno, e.apellidoMaterno, e.puesto 
                FROM usuario u 
                INNER JOIN empleado e ON u.idEmpleado = e.idEmpleado 
                ORDER BY u.fechaCreacion DESC
                """;

            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                usuarios.add(mapearUsuarioCompleto(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error al listar usuarios: " + e.getMessage());
            e.printStackTrace();
        } finally {
            cerrarRecursos(rs, stmt, conn);
        }
        return usuarios;
    }

    @Override
    public boolean actualizarUsuario(Usuario usuario) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            String sql = """
                UPDATE usuario SET 
                    username = ?, contraseña = ?, nivel = ?
                WHERE idUsuario = ?
                """;

            stmt = conn.prepareStatement(sql);

            String passwordHash = PasswordHasher.hashPassword(usuario.getPassword());

            stmt.setString(1, usuario.getUsername());
            stmt.setString(2, passwordHash);
            stmt.setString(3, usuario.getNivel());
            stmt.setString(4, usuario.getIdUsuario());

            int resultado = stmt.executeUpdate();
            return resultado > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar usuario: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            cerrarRecursos(null, stmt, conn);
        }
    }

    @Override
    public boolean eliminarUsuario(String idUsuario) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            String sql = "DELETE FROM usuario WHERE idUsuario = ?";

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, idUsuario);

            int resultado = stmt.executeUpdate();
            return resultado > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar usuario: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            cerrarRecursos(null, stmt, conn);
        }
    }

    @Override
    public boolean cambiarEstadoUsuario(String idUsuario, String nuevoEstado) {
        return eliminarUsuario(idUsuario);
    }

    @Override
    public List<String> obtenerEmpleadosSinUsuario() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<String> empleadosSinUsuario = new ArrayList<>();

        try {
            conn = DatabaseConnection.getConnection();
            String sql = """
                SELECT e.idEmpleado, e.nombre, e.apellidoPaterno, e.apellidoMaterno
                FROM empleado e
                LEFT JOIN usuario u ON e.idEmpleado = u.idEmpleado
                WHERE u.idEmpleado IS NULL AND e.estado = 'A'
                ORDER BY e.nombre
                """;

            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String info = rs.getString("idEmpleado") + " - " +
                        rs.getString("nombre") + " " +
                        rs.getString("apellidoPaterno") + " " +
                        rs.getString("apellidoMaterno");
                empleadosSinUsuario.add(info);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener empleados sin usuario: " + e.getMessage());
            e.printStackTrace();
        } finally {
            cerrarRecursos(rs, stmt, conn);
        }
        return empleadosSinUsuario;
    }

    @Override
    public boolean usernameExiste(String username) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            String sql = "SELECT COUNT(*) as total FROM usuario WHERE username = ?";

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("total") > 0;
            }

        } catch (SQLException e) {
            System.err.println("Error al verificar username: " + e.getMessage());
            e.printStackTrace();
        } finally {
            cerrarRecursos(rs, stmt, conn);
        }
        return false;
    }

    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();

        usuario.setIdEmpleado(rs.getString("idEmpleado"));
        usuario.setIdUsuario(rs.getString("idUsuario"));
        usuario.setUsername(rs.getString("username"));
        usuario.setPassword(rs.getString("contraseña"));

        Date fecha = rs.getDate("fechaCreacion");
        if (fecha != null) {
            usuario.setFechaCreacion(fecha.toLocalDate());
        }

        usuario.setNivel(rs.getString("nivel"));

        return usuario;
    }

    private Usuario mapearUsuarioCompleto(ResultSet rs) throws SQLException {
        Usuario usuario = mapearUsuario(rs);
        return usuario;
    }

    private String generarIdUsuario(Connection conn) throws SQLException {
        String sql = "SELECT COUNT(*) as total FROM usuario";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                int total = rs.getInt("total") + 1;
                return String.format("USU%03d", total);
            }

        } catch (SQLException e) {
            System.err.println("Error al generar ID usuario: " + e.getMessage());
            throw e;
        }

        return "USU001";
    }

    private void cerrarRecursos(ResultSet rs, Statement stmt, Connection conn) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
        } catch (SQLException e) {
            System.err.println("Error al cerrar recursos: " + e.getMessage());
        }
    }
}