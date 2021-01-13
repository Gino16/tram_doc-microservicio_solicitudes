package com.tramite_documentario.microservicios.backend.microserviciosolicitudes.models.entity;

import javax.persistence.*;

@Entity
@Table(name = "rol_solicitudes")
public class RolSolicitud {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol_solicitud")
    private Long id;

    private String nombre;

    public RolSolicitud() {
    }

    public RolSolicitud(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
