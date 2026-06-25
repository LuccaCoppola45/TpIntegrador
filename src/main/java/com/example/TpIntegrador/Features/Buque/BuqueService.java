package com.example.TpIntegrador.Features.Buque;

import com.example.TpIntegrador.Enums.EstadoOperativo;
import com.example.TpIntegrador.Exceptions.BuqueExceptions.BuqueCargaActivaException;
import com.example.TpIntegrador.Exceptions.BuqueExceptions.BuqueDuplicatedException;
import com.example.TpIntegrador.Exceptions.BuqueExceptions.BuqueNotFoundException;
import com.example.TpIntegrador.Exceptions.NotActiveException;
import com.example.TpIntegrador.Features.Buque.Dtos.BuqueAsignarPuertoRequest;
import com.example.TpIntegrador.Features.Buque.Dtos.BuqueRequest;
import com.example.TpIntegrador.Features.Buque.Dtos.BuqueResponse;
import com.example.TpIntegrador.Features.Buque.Mapper.Buquemapper;
import com.example.TpIntegrador.Features.Contenedor.ContenedorEntity;
import com.example.TpIntegrador.Features.Contenedor.ContenedorRepository;
import com.example.TpIntegrador.Features.Puerto.PuertoEntity;
import com.example.TpIntegrador.Features.Puerto.PuertoRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class BuqueService implements IBuqueService{

    private final BuqueRepository buqueRepository;
    private final Buquemapper  buquemapper;
    private final PuertoRepository puertoRepository;
    private ContenedorRepository contenedorRepository;

    public BuqueService(BuqueRepository buqueRepository, Buquemapper buquemapper,  PuertoRepository puertoRepository,  ContenedorRepository contenedorRepository) {
        this.buqueRepository = buqueRepository;
        this.buquemapper = buquemapper;
        this.puertoRepository = puertoRepository;
        this.contenedorRepository = contenedorRepository;
    }

    @Override
    public BuqueResponse crearBuque(BuqueRequest buqueRequest) {
        if (buqueRepository.existsByMatriculaImo(buqueRequest.getMatriculaImo())) {
            throw new BuqueDuplicatedException("Ya existe un buque con esa matrícula.");
        }
        BuqueEntity entity = buquemapper.toEntity(buqueRequest);
        entity.setActivo(true); // Por defecto, al crear, está activo

        BuqueEntity buqueEntity = buqueRepository.save(entity);
        return buquemapper.toDto(buqueEntity);
    }

    @Override
    public BuqueResponse actualizarBuque(long id, BuqueRequest buqueRequest) {
        BuqueEntity entity = buqueRepository.findById(id)
                .orElseThrow(() -> new BuqueNotFoundException("Buque no encontrado"));

        buquemapper.updateEntity(buqueRequest, entity);
        return buquemapper.toDto(buqueRepository.save(entity));
    }

    @Override
    public void eliminarBuque(long id) {
        BuqueEntity buque = buqueRepository.findById(id)
                .orElseThrow(() -> new BuqueNotFoundException("Buque no encontrado"));

        if (EstadoOperativo.EN_RUTA.equals(buque.getEstadoOperativo())) {
            throw new BuqueCargaActivaException("No se puede dar de baja: el buque está EN_RUTA.");
        }

        List<ContenedorEntity> contenedoresAborordo = contenedorRepository.findByBuqueAndActivoTrue(buque);

        if (!contenedoresAborordo.isEmpty()) {
            throw new BuqueCargaActivaException("No se puede dar de baja: el buque tiene "
                    + contenedoresAborordo.size() + " contenedor(es) activo(s) a bordo.");
        }
        if(buque.getActivo() == false){
            throw new NotActiveException("No se puede dar de baja un buque que ya esta dado de baja.");
        }
        buque.setActivo(false);
        buqueRepository.save(buque);
    }

    @Override
    public BuqueResponse obtenerBuque(long id) {
        BuqueEntity buque = buqueRepository.findById(id)
                .orElseThrow(() -> new BuqueNotFoundException("Buque no encontrado con id "+ id));
        return buquemapper.toDto(buque);
    }

    @Override
    public List<BuqueResponse> obtenerBuques() {
        return buquemapper.toEntityList(buqueRepository.findAll());
    }

    @Override
    public BuqueResponse asignarRuta(Long id, BuqueAsignarPuertoRequest buqueRequest) {
        BuqueEntity buque = buqueRepository.findById(id)
                .orElseThrow(() -> new BuqueNotFoundException("Buque no encontrado"));

        // Buscamos los puertos y actualizamos la relación
        List<PuertoEntity> puertos = puertoRepository.findAllById(buqueRequest.getPuertoIds());
        buque.setRutasComerciales(new HashSet<>(puertos));

        return buquemapper.toDto(buqueRepository.save(buque));
    }
}
