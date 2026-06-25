package com.example.TpIntegrador.Features.Buque.Dtos;

import com.example.TpIntegrador.Enums.EstadoOperativo;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class BuqueRequest{
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    @NotBlank(message = "La matricula IMO es obligatoria")
    private String matriculaImo;
    @NotNull(message = "La capacidad maxima es obligatoria")
    @Positive(message = "La capacidad debe ser un valor positivo")
    private Double capacidadMaximaToneladas;
    @NotNull(message = "El estado es obligatorio")
    private EstadoOperativo estadoOperativo;
    @NotNull(message = "La fecha de salida es obligatoria")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaSalida;

}