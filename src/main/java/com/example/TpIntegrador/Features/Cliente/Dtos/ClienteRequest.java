package com.example.TpIntegrador.Features.Cliente.Dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class ClienteRequest{
    @Size(message = "La razón social debe tener entre 3 y 100 caracteres", min = 3, max = 100)
    @NotBlank(message = "La razón social es obligatoria")
    private String razon_social;
    @Pattern(message = "El DNI debe tener entre 7 y 10 números", regexp = "^\\d{7,10}$")
    @NotBlank(message = "El DNI es obligatorio")
    private String dni;
    @NotNull(message = "Debe indicar si el cliente es vip")
    private Boolean es_vip;
}