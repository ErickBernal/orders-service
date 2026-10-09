package com.telecom.crud.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CambioEstadoRequest(
        @NotBlank(message = "obligatorio") String estado,
        @Size(max = 200, message = "solo 200 caracterres") String observacion
) {
}
