package org.example.controller;

import org.example.dao.ClienteDAO;
import org.example.dao.ClienteDAOImpl;
import org.example.model.Cliente;
import javafx.scene.control.Alert;
import java.util.List;

public class ClienteController {
    private ClienteDAO clienteDAO;

    public ClienteController() {
        this.clienteDAO = new ClienteDAOImpl();
    }

    public boolean registrarCliente(String idCliente, String nombre, String apellidoPaterno,
                                    String apellidoMaterno, String direccion, String telefono) {
        try {
            if (idCliente == null || idCliente.trim().isEmpty()) {
                mostrarAlerta("Error", "ID Cliente es requerido", Alert.AlertType.ERROR);
                return false;
            }

            if (nombre == null || nombre.trim().isEmpty()) {
                mostrarAlerta("Error", "Nombre es requerido", Alert.AlertType.ERROR);
                return false;
            }

            if (apellidoPaterno == null || apellidoPaterno.trim().isEmpty()) {
                mostrarAlerta("Error", "Apellido Paterno es requerido", Alert.AlertType.ERROR);
                return false;
            }

            Cliente cliente = new Cliente();
            cliente.setIdCliente(idCliente);
            cliente.setNombre(nombre);
            cliente.setApellidoPaterno(apellidoPaterno);
            cliente.setApellidoMaterno(apellidoMaterno != null ? apellidoMaterno : "");
            cliente.setDireccion(direccion != null ? direccion : "");
            cliente.setTelefono(telefono != null ? telefono : "");

            boolean exito = clienteDAO.registrarCliente(cliente);

            if (exito) {
                mostrarAlerta("Éxito", "Cliente registrado correctamente", Alert.AlertType.INFORMATION);
                return true;
            } else {
                mostrarAlerta("Error", "No se pudo registrar el cliente", Alert.AlertType.ERROR);
                return false;
            }

        } catch (Exception e) {
            mostrarAlerta("Error", "Error al registrar cliente: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
            return false;
        }
    }

    public List<Cliente> listarClientes() {
        try {
            return clienteDAO.listarTodos();
        } catch (Exception e) {
            System.err.println("Error al listar clientes: " + e.getMessage());
            e.printStackTrace();
            return new java.util.ArrayList<>();
        }
    }

    public boolean actualizarCliente(Cliente cliente) {
        try {
            if (cliente == null) {
                mostrarAlerta("Error", "Cliente no puede ser nulo", Alert.AlertType.ERROR);
                return false;
            }

            if (cliente.getNombre() == null || cliente.getNombre().trim().isEmpty()) {
                mostrarAlerta("Error", "Nombre es requerido", Alert.AlertType.ERROR);
                return false;
            }

            boolean exito = clienteDAO.actualizarCliente(cliente);

            if (exito) {
                mostrarAlerta("Éxito", "Cliente actualizado correctamente", Alert.AlertType.INFORMATION);
                return true;
            } else {
                mostrarAlerta("Error", "No se pudo actualizar el cliente", Alert.AlertType.ERROR);
                return false;
            }

        } catch (Exception e) {
            mostrarAlerta("Error", "Error al actualizar cliente: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarCliente(String idCliente) {
        try {
            if (idCliente == null || idCliente.trim().isEmpty()) {
                mostrarAlerta("Error", "ID Cliente es requerido", Alert.AlertType.ERROR);
                return false;
            }

            boolean exito = clienteDAO.eliminarCliente(idCliente);

            if (exito) {
                mostrarAlerta("Éxito", "Cliente eliminado correctamente", Alert.AlertType.INFORMATION);
                return true;
            } else {
                mostrarAlerta("Error", "No se pudo eliminar el cliente", Alert.AlertType.ERROR);
                return false;
            }

        } catch (Exception e) {
            mostrarAlerta("Error", "Error al eliminar cliente: " + e.getMessage(), Alert.AlertType.ERROR);
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