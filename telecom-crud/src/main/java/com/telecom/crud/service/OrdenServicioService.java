package com.telecom.crud.service;

import com.telecom.crud.dto.CambioEstadoRequest;
import com.telecom.crud.dto.CrearOrdenRequest;
import com.telecom.crud.dto.HistorialResponse;
import com.telecom.crud.dto.OrdenResponse;
import com.telecom.crud.model.EstadoOrden;
import com.telecom.crud.model.HistorialOrden;
import com.telecom.crud.model.OrdenServicio;
import com.telecom.crud.repository.EstadoOrdenRepository;
import com.telecom.crud.repository.HistorialOrdenRepository;
import com.telecom.crud.repository.OrdenServicioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrdenServicioService {

    private final OrdenServicioRepository ordenRepo;
    private final EstadoOrdenRepository estadoRepo;
    private final HistorialOrdenRepository historialRepo;

    public OrdenServicioService(OrdenServicioRepository ordenRepo,
            EstadoOrdenRepository estadoRepo,
            HistorialOrdenRepository historialRepo) {
        this.ordenRepo = ordenRepo;
        this.estadoRepo = estadoRepo;
        this.historialRepo = historialRepo;
    }

    public OrdenResponse crear(CrearOrdenRequest req, String usuario) {
        if (ordenRepo.existsByNumeroOrden(req.numeroOrden())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Ya existe una orden con el número " + req.numeroOrden());
        }

        EstadoOrden inicial = estadoRepo.findByNombreEstado(TransicionesEstado.ESTADO_INICIAL)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                        "El estado " + TransicionesEstado.ESTADO_INICIAL + " no existe en ESTADO_ORDEN"));

        OrdenServicio orden = new OrdenServicio();
        orden.setNumeroOrden(req.numeroOrden());
        orden.setIdCliente(req.idCliente());
        orden.setTipoServicio(req.tipoServicio());
        orden.setEstado(inicial);
        ordenRepo.save(orden);

        historialRepo.save(new HistorialOrden(orden, null, inicial, usuario, "Orden creada"));
        return OrdenResponse.de(orden);
    }

    @Transactional(readOnly = true)
    public OrdenResponse obtener(Long id) {
        return OrdenResponse.de(buscar(id));
    }

    @Transactional(readOnly = true)
    public List<OrdenResponse> listar() {
        return ordenRepo.findAllConEstado().stream().map(OrdenResponse::de).toList();
    }

    /**
     * Actualizar estado respetando las reglas de transicion.
     */
    public OrdenResponse cambiarEstado(Long id, CambioEstadoRequest req, String usuario) {
        OrdenServicio orden = buscar(id);
        String actual = orden.getEstado().getNombreEstado();
        String destino = req.estado().trim().toUpperCase(Locale.ROOT);

        EstadoOrden nuevo = estadoRepo.findByNombreEstado(destino)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "El estado '" + req.estado() + "' no existe. Valores válidos: " + nombresEstados()));

        if (actual.equals(destino)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "La orden ya se encuentra en estado " + actual);
        }
        if (!TransicionesEstado.permitida(actual, destino)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Transición no permitida: " + actual + " -> " + destino
                    + ". Estados permitidos desde " + actual + ": "
                    + TransicionesEstado.siguientes(actual));
        }

        EstadoOrden anterior = orden.getEstado();
        orden.setEstado(nuevo);
        ordenRepo.save(orden);
        historialRepo.save(new HistorialOrden(orden, anterior, nuevo, usuario, req.observacion()));
        return OrdenResponse.de(orden);
    }

    @Transactional(readOnly = true)
    public List<HistorialResponse> historial(Long id) {
        buscar(id);
        return historialRepo.findHistorial(id).stream().map(HistorialResponse::de).toList();
    }

    private OrdenServicio buscar(Long id) {
        return ordenRepo.findByIdConEstado(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Orden no encontrada: " + id));
    }

    private String nombresEstados() {
        return estadoRepo.findAll().stream()
                .map(EstadoOrden::getNombreEstado)
                .sorted()
                .collect(Collectors.joining(", ", "[", "]"));
    }
}
