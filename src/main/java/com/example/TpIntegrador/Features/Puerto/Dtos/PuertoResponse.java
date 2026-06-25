package com.example.TpIntegrador.Features.Puerto.Dtos;

import com.example.TpIntegrador.Features.Buque.Dtos.BuqueResponse;
import com.example.TpIntegrador.Features.Contenedor.Dtos.ContenedorResponse;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PuertoResponse {
    private long puerto_id;
    private String nombre;
    private String pais;
    private boolean activo;
    private Set<Long> buquesIds = new HashSet<>();
    private Set<Long> contenedoresIds = new HashSet<>();
}