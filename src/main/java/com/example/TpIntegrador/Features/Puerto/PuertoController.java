package com.example.TpIntegrador.Features.Puerto;

import com.example.TpIntegrador.Features.Puerto.Dtos.PuertoAsignarBuque;
import com.example.TpIntegrador.Features.Puerto.Dtos.PuertoRequest;
import com.example.TpIntegrador.Features.Puerto.Dtos.PuertoResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/puertos")
public class PuertoController {
    private final IPuertoService puertoService;

    public PuertoController(IPuertoService puertoService) {
        this.puertoService = puertoService;
    }

    @PostMapping
    public ResponseEntity<PuertoResponse> crear(@Valid @RequestBody PuertoRequest request) {
        return new ResponseEntity<>(puertoService.crearPuerto(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PuertoResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(puertoService.obtenerPuerto(id));
    }

    // Listar todos los activos (Lo que normalmente usaría el frontend)
    @GetMapping("/activos")
    public ResponseEntity<List<PuertoResponse>> obtenerActivos() {
        return ResponseEntity.ok(puertoService.obtenerPuertosActivos());
    }

    // Listar todos (incluyendo inactivos, útil para auditoría)
    @GetMapping
    public ResponseEntity<List<PuertoResponse>> obtenerTodos() {
        return ResponseEntity.ok(puertoService.obtenerPuertos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PuertoResponse> actualizar(@PathVariable Long id, @Valid @RequestBody PuertoRequest request) {
        return ResponseEntity.ok(puertoService.actualizarPuerto(id, request));
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> darDeBaja(@PathVariable Long id) {
        puertoService.eliminarPuerto(id);
        return ResponseEntity.noContent().build();
    }
}
