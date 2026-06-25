package com.example.TpIntegrador.Features.Cliente;

import com.example.TpIntegrador.Features.Contenedor.ContenedorEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cliente_id;

    @Column(nullable = false)
    private String razon_social;
    @Column(nullable = false, unique = true)
    private String dni;
    @Column(nullable = false)
    private Boolean es_vip;
    @Column(nullable = false)
    private Boolean activo = true;

    @OneToMany(mappedBy = "cliente")
    private Set<ContenedorEntity> contenedores = new HashSet<>();
}
