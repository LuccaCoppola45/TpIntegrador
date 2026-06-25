package com.example.TpIntegrador.Config;

import com.example.TpIntegrador.Features.Contenedor.ContenedorEntity;
import com.example.TpIntegrador.Features.Contenedor.Dtos.ContenedorResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.Banner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper =  new ModelMapper();
        //Solo hace que mape los nombres que son exactamente iguales
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        //basicamente le indique al model mapper los caminos para que pueda mapear estos nombres en el contenedorentity
        mapper.createTypeMap(ContenedorEntity.class, ContenedorResponse.class)
                .addMapping(src -> src.getCliente().getRazon_social(), ContenedorResponse::setRazonSocial)
                .addMapping(src -> src.getPuertoDestino().getNombre(), ContenedorResponse::setNombrePuertoDestino)
                .addMapping(src -> src.getBuque() != null ? src.getBuque().getNombre() : null, ContenedorResponse::setNombreBuque);

        // Le decimos a ModelMapper que para ContenedorEntity,
        // no intente mapear automáticamente el objeto buque entero
        mapper.typeMap(ContenedorEntity.class, ContenedorResponse.class).addMappings(m -> {
            m.skip(ContenedorResponse::setNombreBuque); // O el nombre del setter que tengas
        });
        return mapper;
    }
}
