package com.example.TpIntegrador.Features.Puerto;

import com.example.TpIntegrador.Features.Buque.BuqueEntity;
import com.example.TpIntegrador.Features.Contenedor.ContenedorEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PuertoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long puerto_id;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String pais;
    private boolean activo;

    @ManyToMany(mappedBy = "rutasComerciales")
    private Set<BuqueEntity> buques = new HashSet<>();

    @OneToMany(mappedBy = "puertoDestino")
    private Set<ContenedorEntity> contenedoresDestino = new HashSet<>();
}
