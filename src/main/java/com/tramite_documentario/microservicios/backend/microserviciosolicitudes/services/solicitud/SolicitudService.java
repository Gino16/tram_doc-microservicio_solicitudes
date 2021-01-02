package com.tramite_documentario.microservicios.backend.microserviciosolicitudes.services.solicitud;

import com.tramite_documentario.microservicio.backend.commonmicroservicios.services.CommonService;
import com.tramite_documentario.microservicios.backend.microserviciosolicitudes.models.entity.Solicitud;
import com.tramite_documentario.microservicios.backend.microserviciosolicitudes.models.entity.TipoSolicitud;

import java.util.List;
import java.util.Optional;

public interface SolicitudService extends CommonService<Solicitud> {



    public List<TipoSolicitud> findAllTipoSolicitudes();
}
