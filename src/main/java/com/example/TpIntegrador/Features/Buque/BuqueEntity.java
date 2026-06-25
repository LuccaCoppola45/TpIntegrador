package com.example.TpIntegrador.Features.Buque;

import com.example.TpIntegrador.Enums.EstadoOperativo;
import com.example.TpIntegrador.Features.Contenedor.ContenedorEntity;
import com.example.TpIntegrador.Features.Puerto.PuertoEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class BuqueEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long buque_id;

    private String nombre;

    @Column(name = "matricula_imo")
    private String matriculaImo;

    @Column(name = "fecha_salida")
    private LocalDate fechaSalida;

    private  boolean enMantenimiento = false;

    @Column(name = "capacidad_maxima_toneladas")
    private Double capacidadMaximaToneladas;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_operativo")
    private EstadoOperativo estadoOperativo;

    private Boolean activo = true;

    // Relación Many-to-Many con Puerto
    @ManyToMany
    @JoinTable(
            name = "buque_puerto", // Nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "buque_id"),
            inverseJoinColumns = @JoinColumn(name = "puerto_id")
    )
    private Set<PuertoEntity> rutasComerciales = new HashSet<>();

    @OneToMany(mappedBy = "buque", cascade = CascadeType.ALL)
    private Set<ContenedorEntity> contenedores = new HashSet<>();
}
