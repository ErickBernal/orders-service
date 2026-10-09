package com.telecom.crud.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CrearOrdenRequest(
        @NotBlank(message = "obligatorio")
        @Size(max = 30, message = "30 caracteres") String numeroOrden,
        @NotNull(message = "obligatorio")
        @Positive(message = "positivo") Long idCliente,
        @NotBlank(message = "positivo")
        @Size(max = 50, message = "50 caracteres") String tipoServicio
) {
}
