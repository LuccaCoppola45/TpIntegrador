package com.example.TpIntegrador.Features.Contenedor.Dtos;

import com.example.TpIntegrador.Features.Buque.BuqueEntity;
import com.example.TpIntegrador.Features.Cliente.ClienteEntity;
import com.example.TpIntegrador.Features.Puerto.PuertoEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContenedorResponse{
    private long contenedor_id;
    private String codigoIdentificacion;
    private Double pesoToneladas;
    private Boolean activo;
    private Double precioEmbarque;
    private String RazonSocial;
    private Boolean esVip;
    private String nombrePuertoDestino;
    private String nombreBuque;
}