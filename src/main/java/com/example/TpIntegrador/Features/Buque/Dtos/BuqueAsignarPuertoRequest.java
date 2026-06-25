package com.example.TpIntegrador.Features.Buque.Dtos;

import lombok.Data;

import java.util.Set;

@Data
public class BuqueAsignarPuertoRequest {
    private Set<Long> puertoIds;
}
