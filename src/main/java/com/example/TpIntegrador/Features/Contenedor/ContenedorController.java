package com.example.TpIntegrador.Features.Contenedor;

import com.example.TpIntegrador.Features.Contenedor.Dtos.ContenedorEmbarqueRequest;
import com.example.TpIntegrador.Features.Contenedor.Dtos.ContenedorRequest;
import com.example.TpIntegrador.Features.Contenedor.Dtos.ContenedorResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/contenedores")
public class ContenedorController {
    private final IContenedorService contenedorService;

    public ContenedorController(IContenedorService contenedorService) {
        this.contenedorService = contenedorService;
    }

    @PostMapping
    public ResponseEntity<ContenedorResponse> crear(@Valid @RequestBody ContenedorRequest request) {
        return new ResponseEntity<>(contenedorService.crear(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContenedorResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(contenedorService.obtenerPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<ContenedorResponse>> obtenerTodos() {
        return ResponseEntity.ok(contenedorService.obtenerTodos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContenedorResponse> actualizar(@PathVariable Long id, @Valid @RequestBody ContenedorRequest request) {
        return ResponseEntity.ok(contenedorService.actualizar(id, request));
    }

    @PatchMapping("/{id}/embarcar")
    public ResponseEntity<String> embarcar(@PathVariable Long id, @RequestBody ContenedorEmbarqueRequest request) {
        // Obtenemos el buqueId del cuerpo del JSON
        contenedorService.embarcar(id, request.getBuqueId());
        return ResponseEntity.ok("Contenedor embarcado exitosamente.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> darDeBaja(@PathVariable Long id) {
        contenedorService.darDeBaja(id);
        return ResponseEntity.noContent().build();
    }
}
