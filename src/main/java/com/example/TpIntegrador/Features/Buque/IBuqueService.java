package com.example.TpIntegrador.Features.Buque;

import com.example.TpIntegrador.Features.Buque.Dtos.BuqueAsignarPuertoRequest;
import com.example.TpIntegrador.Features.Buque.Dtos.BuqueRequest;
import com.example.TpIntegrador.Features.Buque.Dtos.BuqueResponse;

import java.util.List;

public interface IBuqueService {
    BuqueResponse crearBuque(BuqueRequest buqueRequest);
    BuqueResponse actualizarBuque(long id, BuqueRequest buqueRequest);
    void eliminarBuque(long id);
    BuqueResponse obtenerBuque(long id);
    List<BuqueResponse> obtenerBuques();
    BuqueResponse asignarRuta(Long id, BuqueAsignarPuertoRequest  buqueRequest);
    void alternarMantenimiento(Long buqueId);
}
