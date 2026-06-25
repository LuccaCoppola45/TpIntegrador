package com.example.TpIntegrador.Features.Contenedor.Dtos;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContenedorEmbarqueRequest {
    @NotNull(message = "El ID del buque es obligatorio")
    private Long buqueId;
}
