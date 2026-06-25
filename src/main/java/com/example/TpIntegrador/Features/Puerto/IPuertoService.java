package com.example.TpIntegrador.Features.Puerto;

import com.example.TpIntegrador.Features.Puerto.Dtos.PuertoAsignarBuque;
import com.example.TpIntegrador.Features.Puerto.Dtos.PuertoRequest;
import com.example.TpIntegrador.Features.Puerto.Dtos.PuertoResponse;

import java.util.List;

public interface IPuertoService {
    PuertoResponse crearPuerto(PuertoRequest puertoRequest);
    PuertoResponse actualizarPuerto(long id, PuertoRequest puertoRequest);
    void eliminarPuerto(long id);
    PuertoResponse obtenerPuerto(long id);
    List<PuertoResponse> obtenerPuertosActivos();
    List<PuertoResponse> obtenerPuertos();
}
