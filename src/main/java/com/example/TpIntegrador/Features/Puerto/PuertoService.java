package com.example.TpIntegrador.Features.Puerto;

import com.example.TpIntegrador.Exceptions.BuqueExceptions.BuqueCargaActivaException;
import com.example.TpIntegrador.Exceptions.NotActiveException;
import com.example.TpIntegrador.Exceptions.PuertoExceptions.PuertoDuplicatedException;
import com.example.TpIntegrador.Exceptions.PuertoExceptions.PuertoNotFoundException;
import com.example.TpIntegrador.Features.Buque.BuqueRepository;
import com.example.TpIntegrador.Features.Puerto.Dtos.PuertoRequest;
import com.example.TpIntegrador.Features.Puerto.Dtos.PuertoResponse;
import com.example.TpIntegrador.Features.Puerto.Mapper.PuertoMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PuertoService implements IPuertoService{

    private final PuertoRepository puertoRepository;
    private final PuertoMapper puertoMapper;
    private final BuqueRepository buqueRepository;

    public PuertoService(PuertoRepository puertoRepository, PuertoMapper puertoMapper,  BuqueRepository buqueRepository) {
        this.puertoRepository = puertoRepository;
        this.puertoMapper = puertoMapper;
        this.buqueRepository = buqueRepository;
    }

    @Override
    public PuertoResponse crearPuerto(PuertoRequest puertoRequest) {
       String nombre = puertoRequest.getNombre().trim();
       String pais = puertoRequest.getPais().trim();

       if(puertoRepository.existsByNombreIgnoreCaseAndPaisIgnoreCase(nombre, pais)){
           throw new PuertoDuplicatedException("Ya existe el puerto con el nombre "+nombre+" en "+pais);
       }
       PuertoEntity nuevopuerto = puertoMapper.toEntity(puertoRequest);
       nuevopuerto.setActivo(true);

       PuertoEntity puertoEntity = puertoRepository.save(nuevopuerto);
       return puertoMapper.toResponse(puertoEntity);
    }

    @Override
    public PuertoResponse actualizarPuerto(long id, PuertoRequest puertoRequest) {
        PuertoEntity puertoExistente = puertoRepository.findById(id)
                .orElseThrow(() -> new PuertoNotFoundException("Puerto no encontrado con ID: " + id));

        // 2. Validación de negocio: Evitar duplicados si cambió el nombre o país
        // (Solo validamos si el nombre/país actual es distinto al que se quiere poner)
        if (!puertoExistente.getNombre().equalsIgnoreCase(puertoRequest.getNombre()) ||
                !puertoExistente.getPais().equalsIgnoreCase(puertoRequest.getPais())) {

            if (puertoRepository.existsByNombreIgnoreCaseAndPaisIgnoreCase(puertoRequest.getNombre(), puertoRequest.getPais())) {
                throw new PuertoDuplicatedException("Ya existe otro puerto con ese nombre en ese país.");
            }
        }

        // 3. Mapeo de los nuevos datos a la entidad existente
        // Usamos mapper.map(origen, destino) para actualizar los campos

        puertoMapper.updateEntity(puertoRequest,puertoExistente);

        // 4. Guardamos los cambios
        PuertoEntity puertoActualizado = puertoRepository.save(puertoExistente);

        return puertoMapper.toResponse(puertoActualizado);
    }

    @Override
    public void eliminarPuerto(long id) {

        PuertoEntity entity = puertoRepository.findById(id)
                .orElseThrow(()-> new PuertoNotFoundException("No se encontro un puerdo con el id" + id));

        if(!entity.getBuques().isEmpty()){
            throw new BuqueCargaActivaException(
                    "No se puede dar de baja el puerto porque tiene " +
                            entity.getBuques().size() + " buques operando en él."
            );
        }
        if(!entity.isActivo()){
            throw new NotActiveException("No se puede dar de baja un puerto que ya esta dado de baja");
        }
        entity.setActivo(false);
        puertoRepository.save(entity);
    }

    @Override
    public PuertoResponse obtenerPuerto(long id) {
        PuertoEntity entity = puertoRepository.findById(id)
                .orElseThrow(()-> new PuertoNotFoundException("No se encontro un puerdo con el id" + id));
        return puertoMapper.toResponse(entity);
    }

    @Override
    public List<PuertoResponse> obtenerPuertosActivos() {
        List<PuertoEntity> listaEntidades = puertoRepository.findByActivoTrue();
        return puertoMapper.toResponseList(listaEntidades);
    }

    @Override
    public List<PuertoResponse> obtenerPuertos() {
        List<PuertoEntity> listaEntidades = puertoRepository.findAll();
        return puertoMapper.toResponseList(listaEntidades);
    }

}
