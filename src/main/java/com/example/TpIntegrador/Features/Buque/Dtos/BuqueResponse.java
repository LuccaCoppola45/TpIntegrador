package com.example.TpIntegrador.Features.Buque.Dtos;

import com.example.TpIntegrador.Enums.EstadoOperativo;
import com.example.TpIntegrador.Features.Contenedor.Dtos.ContenedorResponse;
import com.example.TpIntegrador.Features.Puerto.Dtos.PuertoResponse;
import com.example.TpIntegrador.Features.Puerto.PuertoEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BuqueResponse {
    private Long buque_id;
    private String nombre;
    private String matriculaImo;
    private Double capacidadMaximaToneladas;
    private EstadoOperativo estadoOperativo;
    private Boolean activo;
    private LocalDate fechaSalida;
    private Set<Long> rutasComercialesIds;
    private Set<Long> contenedoresIds;
}