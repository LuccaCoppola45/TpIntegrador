package com.example.TpIntegrador.Features.Cliente.Dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActualizarClienteDto {
    private Boolean esVip;
    private String razonSocial;
}
