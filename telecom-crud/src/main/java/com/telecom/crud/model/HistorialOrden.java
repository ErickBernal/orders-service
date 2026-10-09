package com.telecom.crud.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "HISTORIAL_ORDEN")
public class HistorialOrden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_HISTORIAL")
    private Long idHistorial;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_ORDEN", nullable = false)
    private OrdenServicio orden;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ESTADO_ANTERIOR")
    private EstadoOrden estadoAnterior;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_ESTADO_NUEVO", nullable = false)
    private EstadoOrden estadoNuevo;

    @Column(name = "FECHA_CAMBIO", nullable = false, updatable = false)
    private LocalDateTime fechaCambio;

    @Column(name = "USUARIO", nullable = false, length = 50)
    private String usuario;

    @Column(name = "OBSERVACION", length = 200)
    private String observacion;

    public HistorialOrden() {
    }

    public HistorialOrden(OrdenServicio orden, EstadoOrden anterior, EstadoOrden nuevo,
            String usuario, String observacion) {
        this.orden = orden;
        this.estadoAnterior = anterior;
        this.estadoNuevo = nuevo;
        this.usuario = usuario;
        this.observacion = observacion;
    }

    @PrePersist
    void alCrear() {
        this.fechaCambio = LocalDateTime.now();
    }

    public Long getIdHistorial() {
        return idHistorial;
    }

    public OrdenServicio getOrden() {
        return orden;
    }

    public EstadoOrden getEstadoAnterior() {
        return estadoAnterior;
    }

    public EstadoOrden getEstadoNuevo() {
        return estadoNuevo;
    }

    public LocalDateTime getFechaCambio() {
        return fechaCambio;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getObservacion() {
        return observacion;
    }
}
