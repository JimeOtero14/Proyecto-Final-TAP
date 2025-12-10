package org.example.dao;

import org.example.model.Documento;
import java.util.List;

public interface DocumentoDAO {
    boolean registrarDocumento(Documento documento);
    List<Documento> listarPorVehiculo(String idVehiculo);
    Documento buscarDocumento(String idVehiculo, String idArchivo);
    boolean eliminarDocumento(String idVehiculo, String idArchivo);
    List<Documento> listarTodosDocumentos();
}