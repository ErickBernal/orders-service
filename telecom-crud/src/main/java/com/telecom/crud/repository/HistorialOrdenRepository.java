package com.telecom.crud.repository;

import com.telecom.crud.model.HistorialOrden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HistorialOrdenRepository extends JpaRepository<HistorialOrden, Long> {

    @Query("""
           select h from HistorialOrden h
           left join fetch h.estadoAnterior
           join fetch h.estadoNuevo
           where h.orden.idOrden = :idOrden
           order by h.idHistorial asc
           """)
    List<HistorialOrden> findHistorial(@Param("idOrden") Long idOrden);
}
