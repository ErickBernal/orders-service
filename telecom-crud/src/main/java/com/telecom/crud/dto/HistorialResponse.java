package com.telecom.crud.dto;

import com.telecom.crud.model.HistorialOrden;

import java.time.LocalDateTime;

public record HistorialResponse(
        Long idHistorial,
        Long idOrden,
        String estadoAnterior,
        String estadoNuevo,
        LocalDateTime fechaCambio,
        String usuario,
        String observacion
) {
    public static HistorialResponse de(HistorialOrden h) {
        return new HistorialResponse(
                h.getIdHistorial(),
                h.getOrden().getIdOrden(),
                h.getEstadoAnterior() == null ? null : h.getEstadoAnterior().getNombreEstado(),
                h.getEstadoNuevo().getNombreEstado(),
                h.getFechaCambio(),
                h.getUsuario(),
                h.getObservacion());
    }
}
