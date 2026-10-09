package com.telecom.crud.controller;

import com.telecom.crud.dto.CambioEstadoRequest;
import com.telecom.crud.dto.CrearOrdenRequest;
import com.telecom.crud.dto.HistorialResponse;
import com.telecom.crud.dto.OrdenResponse;
import com.telecom.crud.service.OrdenServicioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordenes")
public class OrdenServicioController {

    private final OrdenServicioService service;

    public OrdenServicioController(OrdenServicioService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<OrdenResponse> crear(@Valid @RequestBody CrearOrdenRequest req,
            @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(req, jwt.getSubject()));
    }

    @GetMapping
    public List<OrdenResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public OrdenResponse obtener(@PathVariable Long id) {
        return service.obtener(id);
    }

    @PatchMapping("/{id}/estado")
    public OrdenResponse cambiarEstado(@PathVariable Long id,
            @Valid @RequestBody CambioEstadoRequest req,
            @AuthenticationPrincipal Jwt jwt) {
        return service.cambiarEstado(id, req, jwt.getSubject());
    }

    @GetMapping("/{id}/historial")
    public List<HistorialResponse> historial(@PathVariable Long id) {
        return service.historial(id);
    }
}
