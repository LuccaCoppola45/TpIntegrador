package com.example.TpIntegrador.Features.Contenedor;

import com.example.TpIntegrador.Features.Buque.BuqueEntity;
import com.example.TpIntegrador.Features.Cliente.ClienteEntity;
import com.example.TpIntegrador.Features.Puerto.PuertoEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class ContenedorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long contenedor_id;

    @Column(name = "codigo_identificacion", nullable = false, unique = true)
    private String codigoIdentificacion;

    @Column(name = "peso_toneladas")
    private Double pesoToneladas;

    @Column(name = "precio_embarque")
    private Double precioEmbarque;

    private Boolean activo = true;

    // Relación Many-to-One con Cliente
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private ClienteEntity cliente;

    // Relación Many-to-One con Puerto
    @ManyToOne
    @JoinColumn(name = "puerto_id", nullable = false)
    private PuertoEntity puertoDestino;

    // Relación Many-to-One con Buque (puede ser nulo)
    @ManyToOne
    @JoinColumn(name = "buque_id", nullable = true)
    private BuqueEntity buque;
}
