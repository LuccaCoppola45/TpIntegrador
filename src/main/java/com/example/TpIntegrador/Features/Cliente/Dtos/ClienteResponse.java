package com.example.TpIntegrador.Features.Cliente.Dtos;


import com.example.TpIntegrador.Features.Contenedor.Dtos.ContenedorResponse;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponse {
   private long cliente_id;
   private String razon_social;
   private String dni;
   private Boolean es_vip;
   private Boolean activo;
    private Set<ContenedorResponse> contenedores;
}