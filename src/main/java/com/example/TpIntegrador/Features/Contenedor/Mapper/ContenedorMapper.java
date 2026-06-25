package com.example.TpIntegrador.Features.Contenedor.Mapper;

import com.example.TpIntegrador.Features.Contenedor.ContenedorEntity;
import com.example.TpIntegrador.Features.Contenedor.Dtos.ContenedorRequest;
import com.example.TpIntegrador.Features.Contenedor.Dtos.ContenedorResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class ContenedorMapper {
    private final ModelMapper mapper;

    public ContenedorMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public ContenedorEntity toEntity(ContenedorRequest contenedorRequest) {
        return mapper.map(contenedorRequest, ContenedorEntity.class);
    }

    public ContenedorResponse toDto(ContenedorEntity contenedorEntity) {
        return mapper.map(contenedorEntity, ContenedorResponse.class);
    }

    public void updateEntity(ContenedorRequest request, ContenedorEntity entity) {
        // Configuramos el mapper para que ignore el ID de la entidad al actualizar
        // y para que no intente mapear campos null si el request viene incompleto
        mapper.typeMap(ContenedorRequest.class, ContenedorEntity.class)
                .addMappings(m -> m.skip(ContenedorEntity::setContenedor_id)); // Ajusta según el nombre de tu campo ID

        mapper.map(request, entity);
    }

    public List<ContenedorResponse> toEntityList(List<ContenedorEntity> contenedorEntityList) {
        return contenedorEntityList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}
