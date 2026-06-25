package com.example.TpIntegrador.Features.Cliente;

import com.example.TpIntegrador.Features.Cliente.Dtos.ActualizarClienteDto;
import com.example.TpIntegrador.Features.Cliente.Dtos.ClienteRequest;
import com.example.TpIntegrador.Features.Cliente.Dtos.ClienteResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")

@AllArgsConstructor
public class ClienteController {

    private final IClienteService clienteService;

    @PostMapping
    public ResponseEntity<ClienteResponse> crearCliente(@Valid @RequestBody ClienteRequest clienteRequest) {
        ClienteResponse clienteResponse = clienteService.crearCliente(clienteRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable long id) {
        clienteService.dardeBajaCliente(id);
        return ResponseEntity.noContent().build();
    }

    //Listar Activos
    @GetMapping
    public ResponseEntity<List<ClienteResponse>> listarActivos() {
        return ResponseEntity.ok(clienteService.obtenerClientesActivos());

    }
    //Buscar un cliente por id
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> obtenerPorId(@PathVariable long id) {
        return ResponseEntity.ok(clienteService.obtenerClientePorId(id));
    }

    //Listar todos los clientes incluyendo inactivos
    @GetMapping("/todos")
    public ResponseEntity<List<ClienteResponse>> listarTodos() {
        return ResponseEntity.ok(clienteService.obtenerTodosLosClientes());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponse> actualizar(@PathVariable Long id, @RequestBody ActualizarClienteDto dto) {
        return ResponseEntity.ok(clienteService.ActualizarCliente(id, dto));
    }
}