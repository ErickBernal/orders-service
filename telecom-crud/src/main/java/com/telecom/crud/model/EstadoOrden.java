package com.telecom.crud.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ESTADO_ORDEN")
public class EstadoOrden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ESTADO")
    private Long idEstado;

    @Column(name = "NOMBRE_ESTADO", nullable = false, unique = true, length = 20)
    private String nombreEstado;

    public EstadoOrden() {
    }

    public Long getIdEstado() { return idEstado; }
    public void setIdEstado(Long idEstado) { this.idEstado = idEstado; }

    public String getNombreEstado() { return nombreEstado; }
    public void setNombreEstado(String nombreEstado) { this.nombreEstado = nombreEstado; }
}
