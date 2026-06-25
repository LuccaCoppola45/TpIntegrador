package com.example.TpIntegrador.Features.Puerto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PuertoRepository extends JpaRepository<PuertoEntity, Long> {

    List<PuertoEntity> findByActivoTrue();

    @Query("SELECT COUNT(p) > 0 FROM PuertoEntity p WHERE LOWER(p.nombre) = LOWER(:nombre) AND LOWER(p.pais) = LOWER(:pais)")
    boolean existsByNombreIgnoreCaseAndPaisIgnoreCase(@Param("nombre") String nombre, @Param("pais") String pais);
}