package com.tramite_documentario.microservicios.backend.microserviciosolicitudes.models.entity;

import com.tramite_documentario.microservicio.backend.commonpersonas.models.entity.Persona;

import javax.persistence.*;

@Entity
@Table(name = "personas_solicitudes")
public class PersonaSolicitud {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_personas_solicitudes")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_persona")
    private Persona persona;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_solicitud")
    private Solicitud solicitud;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_rol_solicitud")
    private RolSolicitud rolSolicitud;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Solicitud getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
    }

    public RolSolicitud getRolSolicitud() {
        return rolSolicitud;
    }

    public void setRolSolicitud(RolSolicitud rolSolicitud) {
        this.rolSolicitud = rolSolicitud;
    }
}
