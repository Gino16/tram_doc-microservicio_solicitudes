package com.tramite_documentario.microservicios.backend.microserviciosolicitudes.services.personasolicitud;

import com.tramite_documentario.microservicio.backend.commonmicroservicios.services.CommonService;
import com.tramite_documentario.microservicios.backend.microserviciosolicitudes.models.entity.PersonaSolicitud;

import java.util.List;

public interface PersonaSolicitudService extends CommonService<PersonaSolicitud> {

    public List<PersonaSolicitud> saveAll(List<PersonaSolicitud> personaSolicitudes);

    public PersonaSolicitud findPersonaSolicitudEmisorBySolicitud(Long idSolicitud);

    public List<PersonaSolicitud> findPersonaSolicitudReceptoresBySolicitud(Long idSolicitud);
}
