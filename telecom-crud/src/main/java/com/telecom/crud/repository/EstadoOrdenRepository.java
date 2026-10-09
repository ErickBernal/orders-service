package com.telecom.crud.repository;

import com.telecom.crud.model.EstadoOrden;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstadoOrdenRepository extends JpaRepository<EstadoOrden, Long> {
    Optional<EstadoOrden> findByNombreEstado(String nombreEstado);
}
