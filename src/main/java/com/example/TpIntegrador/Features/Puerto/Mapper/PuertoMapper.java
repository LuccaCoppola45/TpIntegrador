package com.example.TpIntegrador.Features.Puerto.Mapper;

import com.example.TpIntegrador.Features.Buque.BuqueEntity;
import com.example.TpIntegrador.Features.Buque.Dtos.BuqueResponse;
import com.example.TpIntegrador.Features.Contenedor.ContenedorEntity;
import com.example.TpIntegrador.Features.Puerto.Dtos.PuertoRequest;
import com.example.TpIntegrador.Features.Puerto.Dtos.PuertoResponse;
import com.example.TpIntegrador.Features.Puerto.PuertoEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@Component
public class PuertoMapper {
    private final ModelMapper mapper;

    public PuertoMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public PuertoEntity toEntity(PuertoRequest dto) {
        return mapper.map(dto, PuertoEntity.class);
    }

    public PuertoResponse toResponse(PuertoEntity entity) {
        PuertoResponse response =  mapper.map(entity, PuertoResponse.class);

        // Esto que hice a continuacion son mapeos manuales para la lista de IDS de BUQUE Y DE CONTENEDORES, porque el modelmapper no los mapeaba
        if (entity.getBuques() != null) {
            Set<Long> ids = entity.getBuques().stream()
                    .map(BuqueEntity::getBuque_id)
                    .collect(Collectors.toSet());
            response.setBuquesIds(ids);
        }
        if (entity.getContenedoresDestino() != null) {
            Set<Long> idsContenedores = entity.getContenedoresDestino().stream()
                    .map(ContenedorEntity::getContenedor_id)
                    .collect(Collectors.toSet());
            response.setContenedoresIds(idsContenedores);
        }

        return response;
    }

    public void updateEntity(PuertoRequest request, PuertoEntity entity) {
        mapper.map(request, entity);
    }

    public List<PuertoResponse> toResponseList(List<PuertoEntity> entities) {
        return entities.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

}
