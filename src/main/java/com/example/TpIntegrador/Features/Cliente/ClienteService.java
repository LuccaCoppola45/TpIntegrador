package com.example.TpIntegrador.Features.Cliente;

import com.example.TpIntegrador.Exceptions.ClienteExceptions.ClienteNotFoundException;
import com.example.TpIntegrador.Exceptions.ClienteExceptions.DniDuplicatedException;
import com.example.TpIntegrador.Exceptions.NotActiveException;
import com.example.TpIntegrador.Features.Cliente.Dtos.ActualizarClienteDto;
import com.example.TpIntegrador.Features.Cliente.Dtos.ClienteRequest;
import com.example.TpIntegrador.Features.Cliente.Dtos.ClienteResponse;
import com.example.TpIntegrador.Features.Cliente.Mapper.ClienteMapper;
import com.example.TpIntegrador.Features.Contenedor.ContenedorEntity;
import com.example.TpIntegrador.Features.Contenedor.ContenedorRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService implements IClienteService{

    private final ClienteRepository clienteRepository;
    private final ClienteMapper  clienteMapper;
    private final ContenedorRepository contenedorRepository;

    public ClienteService(ClienteRepository clienteRepository, ClienteMapper clienteMapper, ContenedorRepository contenedorRepository) {
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
        this.contenedorRepository = contenedorRepository;
    }


    @Override
    public ClienteResponse crearCliente(ClienteRequest clienteRequest) {
        String dni = clienteRequest.getDni();

        if(clienteRepository.existsByDni(dni)){
            throw new DniDuplicatedException("Ya existe un cliente con el dni " + dni);
        }
        ClienteEntity clienteEntity = clienteMapper.toEntity(clienteRequest);
        ClienteEntity clientesaved = clienteRepository.save(clienteEntity);

        return clienteMapper.toDto(clientesaved);
    }


    @Transactional
    @Override
    public void dardeBajaCliente(long ClienteId) {
     ClienteEntity entity = clienteRepository.findById(ClienteId)
             .orElseThrow(()-> new ClienteNotFoundException("No se encontro un cliente con el Id " + ClienteId));
     if(entity.getActivo() == Boolean.FALSE){
         throw new NotActiveException("No se puede dar de baja un cliente que ya esta dado de baja");
     }
        List<ContenedorEntity> contenedores = contenedorRepository.findByClienteAndActivoTrue(entity);

        if (!contenedores.isEmpty()) {
            throw new NotActiveException("No se puede dar de baja al cliente: tiene "
                    + contenedores.size() + " contenedor(es) activo(s) en sistema.");
        }
     entity.setActivo(false);
     clienteRepository.save(entity);
    }

    @Override
    public List<ClienteResponse> obtenerClientesActivos() {
       List<ClienteEntity> clienteEntities = clienteRepository.findByActivoTrue();
        System.out.println("Cantidad de clientes encontrados: " + clienteEntities.size());
       return clienteMapper.toResponseList(clienteEntities);
    }

    @Override
    public List<ClienteResponse> obtenerTodosLosClientes() {
        List<ClienteEntity> clienteEntities = clienteRepository.findAll();
        System.out.println("Cantidad de clientes encontrados: " + clienteEntities.size());
        return clienteMapper.toResponseList(clienteEntities);
    }

    @Override
    public ClienteResponse obtenerClientePorId(long ClienteId) {
        ClienteEntity clienteEntity = clienteRepository.findById(ClienteId)
                .orElseThrow(()-> new ClienteNotFoundException("No se encontro un cliente con el Id " + ClienteId));
        return clienteMapper.toDto(clienteEntity);
    }

    @Override
    public ClienteResponse ActualizarCliente(long ClienteId, ActualizarClienteDto actualizarClienteDto) {
        ClienteEntity cliente = clienteRepository.findById(ClienteId)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente no encontrado"));

        // Actualización de campos
        if (actualizarClienteDto.getRazonSocial() != null) {
            cliente.setRazon_social(actualizarClienteDto.getRazonSocial());
        }

        // Aquí es donde impactas tu lógica de negocio
        cliente.setEs_vip(actualizarClienteDto.getEsVip());

        ClienteEntity clientesaved = clienteRepository.save(cliente);

        return clienteMapper.toDto(clientesaved);
    }
}
