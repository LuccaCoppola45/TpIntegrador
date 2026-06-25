package com.example.TpIntegrador.Features.Contenedor;

import com.example.TpIntegrador.Features.Buque.BuqueEntity;
import com.example.TpIntegrador.Features.Cliente.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContenedorRepository extends JpaRepository<ContenedorEntity, Long> {

    List<ContenedorEntity> findByActivoTrue();
    List<ContenedorEntity> findByClienteAndActivoTrue(ClienteEntity cliente);
    List<ContenedorEntity> findByBuqueAndActivoTrue(BuqueEntity buque);
}
