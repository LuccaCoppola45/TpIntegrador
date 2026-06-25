package com.example.TpIntegrador.Features.Buque;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BuqueRepository extends JpaRepository<BuqueEntity, Long> {

    @Query("SELECT COUNT(b) > 0 FROM BuqueEntity b WHERE b.matriculaImo = :matricula")
    boolean existsByMatriculaImo(@Param("matricula") String matricula);
}