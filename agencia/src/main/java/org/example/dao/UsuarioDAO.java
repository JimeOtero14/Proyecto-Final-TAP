package org.example.dao;

import org.example.model.Usuario;
import java.util.List;

public interface UsuarioDAO {
    boolean registrarUsuario(Usuario usuario);
    Usuario buscarPorUsername(String username);
    List<Usuario> listarTodos();
    boolean actualizarUsuario(Usuario usuario);
    boolean eliminarUsuario(String idUsuario);

    boolean cambiarEstadoUsuario(String idUsuario, String nuevoEstado);
    List<String> obtenerEmpleadosSinUsuario();
    boolean usernameExiste(String username);
}