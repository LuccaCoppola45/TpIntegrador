package com.example.TpIntegrador.Features.Cliente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {

    List<ClienteEntity> findByActivoTrue();
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM ClienteEntity c WHERE c.dni = :dni")//esta query devuelve 0 o 1 si el dni coincide
    boolean existsByDni(@Param("dni") String dni);

}