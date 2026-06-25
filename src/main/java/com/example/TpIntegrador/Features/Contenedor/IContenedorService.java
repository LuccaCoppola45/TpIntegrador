package com.example.TpIntegrador.Features.Contenedor;

import com.example.TpIntegrador.Features.Contenedor.Dtos.ContenedorRequest;
import com.example.TpIntegrador.Features.Contenedor.Dtos.ContenedorResponse;

import java.util.List;

public interface IContenedorService {
    ContenedorResponse crear(ContenedorRequest request);
    ContenedorResponse obtenerPorId(Long id);
    List<ContenedorResponse> obtenerTodos();
    ContenedorResponse actualizar(Long id, ContenedorRequest request);
    void darDeBaja(Long id);
    void embarcar(Long contenedorId, Long buqueId);
}
