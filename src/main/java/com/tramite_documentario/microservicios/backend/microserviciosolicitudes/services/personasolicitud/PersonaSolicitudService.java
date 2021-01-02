package com.tramite_documentario.microservicios.backend.microserviciosolicitudes.services.personasolicitud;

import com.tramite_documentario.microservicios.backend.microserviciosolicitudes.models.entity.PersonaSolicitud;

import java.util.List;

public interface PersonaSolicitudService {

    public PersonaSolicitud findPersonaSolicitudEmisorBySolicitud(Long idSolicitud);

    public List<PersonaSolicitud> findPersonaSolicitudReceptoresBySolicitud(Long idSolicitud);
}
