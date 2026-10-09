package com.telecom.crud.repository;

import com.telecom.crud.model.OrdenServicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrdenServicioRepository extends JpaRepository<OrdenServicio, Long> {

    @Query("select o from OrdenServicio o join fetch o.estado order by o.idOrden desc")
    List<OrdenServicio> findAllConEstado();

    @Query("select o from OrdenServicio o join fetch o.estado where o.idOrden = :id")
    Optional<OrdenServicio> findByIdConEstado(@Param("id") Long id);

    boolean existsByNumeroOrden(String numeroOrden);
}
