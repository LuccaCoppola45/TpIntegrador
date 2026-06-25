package com.example.TpIntegrador.Features.Cliente;

import com.example.TpIntegrador.Features.Cliente.Dtos.ActualizarClienteDto;
import com.example.TpIntegrador.Features.Cliente.Dtos.ClienteRequest;
import com.example.TpIntegrador.Features.Cliente.Dtos.ClienteResponse;

import java.util.List;


public interface IClienteService {
ClienteResponse crearCliente(ClienteRequest clienteRequest);
void dardeBajaCliente(long ClienteId);
List<ClienteResponse> obtenerClientesActivos();
List<ClienteResponse> obtenerTodosLosClientes();
ClienteResponse obtenerClientePorId(long ClienteId);
ClienteResponse ActualizarCliente(long ClienteId, ActualizarClienteDto actualizarClienteDto);
}
