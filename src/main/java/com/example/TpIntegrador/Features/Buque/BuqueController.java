package com.example.TpIntegrador.Features.Buque;

import com.example.TpIntegrador.Features.Buque.Dtos.BuqueAsignarPuertoRequest;
import com.example.TpIntegrador.Features.Buque.Dtos.BuqueRequest;
import com.example.TpIntegrador.Features.Buque.Dtos.BuqueResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/buques")
public class BuqueController {
    private final IBuqueService buqueService;

    public BuqueController(IBuqueService buqueService) {
        this.buqueService = buqueService;
    }

    @PostMapping
    public ResponseEntity<BuqueResponse> crear(@Valid @RequestBody BuqueRequest request) {
        return new ResponseEntity<>(buqueService.crearBuque(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BuqueResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(buqueService.obtenerBuque(id));
    }

    @GetMapping
    public ResponseEntity<List<BuqueResponse>> obtenerTodos() {
        return ResponseEntity.ok(buqueService.obtenerBuques());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BuqueResponse> actualizar(@PathVariable Long id, @Valid @RequestBody BuqueRequest request) {
        return ResponseEntity.ok(buqueService.actualizarBuque(id, request));
    }

    @PatchMapping("/{id}/asignar-ruta")
    public ResponseEntity<BuqueResponse> asignarRuta(@PathVariable Long id, @RequestBody BuqueAsignarPuertoRequest request) {
        return ResponseEntity.ok(buqueService.asignarRuta(id, request));
    }
    @PatchMapping("/{id}/mantenimiento")
    public ResponseEntity<String> cambiarMantenimiento(@PathVariable Long id) {
        buqueService.alternarMantenimiento(id);
        return ResponseEntity.ok("Estado de mantenimiento del buque actualizado.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> darDeBaja(@PathVariable Long id) {
        buqueService.eliminarBuque(id);
        return ResponseEntity.noContent().build();
    }
}
