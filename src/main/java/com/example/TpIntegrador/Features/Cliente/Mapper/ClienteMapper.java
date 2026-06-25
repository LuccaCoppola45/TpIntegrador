package com.example.TpIntegrador.Features.Cliente.Mapper;

import com.example.TpIntegrador.Features.Cliente.ClienteEntity;
import com.example.TpIntegrador.Features.Cliente.Dtos.ClienteRequest;
import com.example.TpIntegrador.Features.Cliente.Dtos.ClienteResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component

public class ClienteMapper {
    private final ModelMapper mapper;

    public ClienteMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public ClienteEntity toEntity(ClienteRequest clienteRequest) {
        return mapper.map(clienteRequest, ClienteEntity.class);
    }

    public ClienteResponse toDto(ClienteEntity clienteEntity) {
        return mapper.map(clienteEntity, ClienteResponse.class);
    }

    public List<ClienteResponse> toResponseList(List<ClienteEntity> clienteEntityList) {
        return clienteEntityList.stream()
                .map(this::toDto)
                .toList();
    }
}
