package com.tramite_documentario.microservicios.backend.microserviciosolicitudes.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tramite_documentario.microservicio.backend.commonpersonas.models.entity.Persona;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "solicitudes")
public class Solicitud {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_solicitud")
    private Long id;

    private String descripcion;

    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_solicitud")
    private TipoSolicitud tipoSolicitud;

    @Transient
    private Persona personaEmisor;


    @Transient
    private List<Persona> personasReceptoras;

    @OneToMany(mappedBy = "solicitud")
    private List<EstadoSolicitud> estadoSolicitudes;


//    @Transient
//    private List<EstadoSolicitud> estadoSolicitud;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TipoSolicitud getTipoSolicitud() {
        return tipoSolicitud;
    }

    public void setTipoSolicitud(TipoSolicitud tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }

    public List<EstadoSolicitud> getEstadoSolicitudes() {
        return estadoSolicitudes;
    }

    public void setEstadoSolicitudes(List<EstadoSolicitud> estadoSolicitudes) {
        this.estadoSolicitudes = estadoSolicitudes;
    }


    public Persona getPersonaEmisor() {
        return personaEmisor;
    }

    public void setPersonaEmisor(Persona personaEmisor) {
        this.personaEmisor = personaEmisor;
    }

    public List<Persona> getPersonasReceptoras() {
        return personasReceptoras;
    }

    public void setPersonasReceptoras(List<Persona> personasReceptoras) {
        this.personasReceptoras = personasReceptoras;
    }
}
