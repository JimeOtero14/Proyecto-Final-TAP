package org.example.controller;

import org.example.dao.UsuarioDAO;
import org.example.dao.UsuarioDAOImpl;
import org.example.model.Usuario;
import javafx.scene.control.Alert;
import java.time.LocalDate;
import java.util.List;

public class UsuarioController {
    private UsuarioDAO usuarioDAO;

    public UsuarioController() {
        this.usuarioDAO = new UsuarioDAOImpl();
    }

    public boolean registrarUsuario(String idEmpleado, String username,
                                    String password, String nivel) {
        try {
            if (idEmpleado == null || idEmpleado.trim().isEmpty()) {
                mostrarAlerta("Error", "ID Empleado es requerido", Alert.AlertType.ERROR);
                return false;
            }

            if (username == null || username.trim().isEmpty()) {
                mostrarAlerta("Error", "Username es requerido", Alert.AlertType.ERROR);
                return false;
            }

            if (password == null || password.trim().isEmpty()) {
                mostrarAlerta("Error", "Contraseña es requerida", Alert.AlertType.ERROR);
                return false;
            }

            if (password.length() < 6) {
                mostrarAlerta("Error", "La contraseña debe tener al menos 6 caracteres", Alert.AlertType.ERROR);
                return false;
            }

            if (nivel == null || !nivel.matches("ADMIN|GERENTE|MECANICO|VENDEDOR|RH")) {
                mostrarAlerta("Error", "Nivel inválido. Use: ADMIN, GERENTE, MECANICO, VENDEDOR o RH", Alert.AlertType.ERROR);
                return false;
            }

            // Verificar si username ya existe
            if (usuarioDAO.usernameExiste(username)) {
                mostrarAlerta("Error", "El username ya está en uso", Alert.AlertType.ERROR);
                return false;
            }

            Usuario usuario = new Usuario();
            usuario.setIdEmpleado(idEmpleado);
            usuario.setUsername(username);
            usuario.setPassword(password);
            usuario.setNivel(nivel.toUpperCase());
            usuario.setFechaCreacion(LocalDate.now());

            boolean exito = usuarioDAO.registrarUsuario(usuario);

            if (exito) {
                mostrarAlerta("Éxito", "Usuario registrado correctamente", Alert.AlertType.INFORMATION);
                return true;
            } else {
                mostrarAlerta("Error", "No se pudo registrar el usuario", Alert.AlertType.ERROR);
                return false;
            }

        } catch (Exception e) {
            mostrarAlerta("Error", "Error al registrar usuario: " + e.getMessage(), Alert.AlertType.ERROR);
            return false;
        }
    }

    public List<Usuario> listarUsuarios() {
        try {
            return usuarioDAO.listarTodos();
        } catch (Exception e) {
            System.err.println("Error al listar usuarios: " + e.getMessage());
            e.printStackTrace();
            return new java.util.ArrayList<>();
        }
    }

    public boolean actualizarUsuario(Usuario usuario) {
        try {
            if (usuario == null) {
                mostrarAlerta("Error", "Usuario no puede ser nulo", Alert.AlertType.ERROR);
                return false;
            }

            if (usuario.getUsername() == null || usuario.getUsername().trim().isEmpty()) {
                mostrarAlerta("Error", "Username es requerido", Alert.AlertType.ERROR);
                return false;
            }

            if (usuario.getPassword() == null || usuario.getPassword().trim().isEmpty()) {
                mostrarAlerta("Error", "Contraseña es requerida", Alert.AlertType.ERROR);
                return false;
            }

            if (usuario.getPassword().length() < 6) {
                mostrarAlerta("Error", "La contraseña debe tener al menos 6 caracteres", Alert.AlertType.ERROR);
                return false;
            }

            boolean exito = usuarioDAO.actualizarUsuario(usuario);

            if (exito) {
                mostrarAlerta("Éxito", "Usuario actualizado correctamente", Alert.AlertType.INFORMATION);
                return true;
            } else {
                mostrarAlerta("Error", "No se pudo actualizar el usuario", Alert.AlertType.ERROR);
                return false;
            }

        } catch (Exception e) {
            mostrarAlerta("Error", "Error al actualizar usuario: " + e.getMessage(), Alert.AlertType.ERROR);
            return false;
        }
    }

    public boolean eliminarUsuario(String idUsuario) {
        try {
            if (idUsuario == null || idUsuario.trim().isEmpty()) {
                mostrarAlerta("Error", "ID Usuario es requerido", Alert.AlertType.ERROR);
                return false;
            }

            boolean exito = usuarioDAO.eliminarUsuario(idUsuario);

            if (exito) {
                mostrarAlerta("Éxito", "Usuario eliminado correctamente", Alert.AlertType.INFORMATION);
                return true;
            } else {
                mostrarAlerta("Error", "No se pudo eliminar el usuario", Alert.AlertType.ERROR);
                return false;
            }

        } catch (Exception e) {
            mostrarAlerta("Error", "Error al eliminar usuario: " + e.getMessage(), Alert.AlertType.ERROR);
            return false;
        }
    }

    public List<String> obtenerEmpleadosSinUsuario() {
        try {
            return ((UsuarioDAOImpl) usuarioDAO).obtenerEmpleadosSinUsuario();
        } catch (Exception e) {
            System.err.println("Error al obtener empleados sin usuario: " + e.getMessage());
            e.printStackTrace();
            return new java.util.ArrayList<>();
        }
    }

    public boolean usernameExiste(String username) {
        try {
            return usuarioDAO.usernameExiste(username);
        } catch (Exception e) {
            System.err.println("Error al verificar username: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}