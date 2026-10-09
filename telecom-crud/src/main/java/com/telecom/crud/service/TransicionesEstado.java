package com.telecom.crud.service;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public final class TransicionesEstado {

    public static final String ESTADO_INICIAL = "CREATED";

    private static final Map<String, Set<String>> PERMITIDAS = Map.of(
            "CREATED", Set.of("VALIDATED", "CANCELLED"),
            "VALIDATED", Set.of("APPROVED", "CANCELLED"),
            "APPROVED", Set.of("IN_PROGRESS", "CANCELLED"),
            "IN_PROGRESS", Set.of("COMPLETED", "CANCELLED"),
            "COMPLETED", Set.of(),
            "CANCELLED", Set.of()
    );

    private TransicionesEstado() {
    }

    public static boolean permitida(String desde, String hacia) {
        return PERMITIDAS.getOrDefault(desde, Set.of()).contains(hacia);
    }

    public static Set<String> siguientes(String desde) {
        return new TreeSet<>(PERMITIDAS.getOrDefault(desde, Set.of()));
    }
}
