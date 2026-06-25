package com.example.TpIntegrador.Features.Buque.Mapper;

import com.example.TpIntegrador.Features.Buque.BuqueEntity;
import com.example.TpIntegrador.Features.Buque.Dtos.BuqueRequest;
import com.example.TpIntegrador.Features.Buque.Dtos.BuqueResponse;
import com.example.TpIntegrador.Features.Contenedor.ContenedorEntity;
import com.example.TpIntegrador.Features.Contenedor.Dtos.ContenedorResponse;
import com.example.TpIntegrador.Features.Puerto.Dtos.PuertoResponse;
import com.example.TpIntegrador.Features.Puerto.PuertoEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@Component
public class Buquemapper {

    private final ModelMapper mapper;

    public Buquemapper(ModelMapper mapper) {
        this.mapper = mapper;
    }


    public BuqueEntity toEntity(BuqueRequest buqueRequest) {
        return mapper.map(buqueRequest, BuqueEntity.class);
    }

    public BuqueResponse toDto(BuqueEntity buqueEntity) {
        BuqueResponse response = mapper.map(buqueEntity, BuqueResponse.class);


        if (buqueEntity.getContenedores() != null) {
            Set<Long> ids = buqueEntity.getContenedores().stream()
                    .map(ContenedorEntity::getContenedor_id) // Extraemos solo el ID
                    .collect(Collectors.toSet());
            response.setContenedoresIds(ids); // Asegúrate de tener este setter
        }

        if (buqueEntity.getRutasComerciales() != null) {
            Set<Long> ids = buqueEntity.getRutasComerciales().stream()
                    .map(PuertoEntity::getPuerto_id) // Extraemos solo el ID
                    .collect(Collectors.toSet());
            response.setRutasComercialesIds(ids); // Asegúrate de tener este setter
        }
        return response;
    }


        public void updateEntity (BuqueRequest request, BuqueEntity entity){
            mapper.map(request, entity);
        }

        public List<BuqueResponse> toEntityList (List < BuqueEntity > buqueEntityList) {
            return buqueEntityList.stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());
        }

}