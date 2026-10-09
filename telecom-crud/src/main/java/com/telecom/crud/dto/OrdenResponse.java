package com.telecom.crud.dto;

import com.telecom.crud.model.OrdenServicio;

import java.time.LocalDateTime;

public record OrdenResponse(
        Long idOrden,
        String numeroOrden,
        Long idCliente,
        String tipoServicio,
        Long idEstado,
        String estado,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaActualizacion
) {
    public static OrdenResponse de(OrdenServicio o) {
        return new OrdenResponse(
                o.getIdOrden(),
                o.getNumeroOrden(),
                o.getIdCliente(),
                o.getTipoServicio(),
                o.getEstado().getIdEstado(),
                o.getEstado().getNombreEstado(),
                o.getFechaCreacion(),
                o.getFechaActualizacion());
    }
}
