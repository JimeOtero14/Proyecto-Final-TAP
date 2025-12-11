package org.example.dao;

import org.example.model.Cliente;
import java.util.List;

public interface ClienteDAO {
    boolean registrarCliente(Cliente cliente);
    Cliente buscarPorId(String idCliente);
    List<Cliente> listarTodos();
    boolean actualizarCliente(Cliente cliente);
    boolean eliminarCliente(String idCliente);
}
