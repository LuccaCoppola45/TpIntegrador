package com.example.TpIntegrador.Features.Contenedor.Dtos;

import com.example.TpIntegrador.Features.Buque.BuqueEntity;
import com.example.TpIntegrador.Features.Cliente.ClienteEntity;
import com.example.TpIntegrador.Features.Puerto.PuertoEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ContenedorRequest{
    @Size(message = "El codigo debe tener tener entre 5 y 20 caracteres", min = 5, max = 20)
    @NotBlank(message = "El codigo de identificacion es obligatorio")
    private String codigoIdentificacion;

    @NotNull(message = "El peso es obligatorio")
    @Positive(message = "El peso debe ser mayor a cero")
    private Double pesoToneladas;

    @NotNull(message = "Debe asignar un cliente al contenedor")
    private Long clienteId;

    @NotNull(message = "Debe asignar un puerto al destino")
    private Long puertoDestinoId;

}