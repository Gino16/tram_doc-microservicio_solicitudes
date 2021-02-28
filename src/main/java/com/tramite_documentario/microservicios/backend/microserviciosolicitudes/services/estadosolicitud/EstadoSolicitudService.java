package com.tramite_documentario.microservicios.backend.microserviciosolicitudes.services.estadosolicitud;

import com.tramite_documentario.microservicio.backend.commonmicroservicios.services.CommonService;
import com.tramite_documentario.microservicios.backend.microserviciosolicitudes.models.entity.Estado;
import com.tramite_documentario.microservicios.backend.microserviciosolicitudes.models.entity.EstadoSolicitud;

import java.util.List;

public interface EstadoSolicitudService extends CommonService<EstadoSolicitud> {
    public List<EstadoSolicitud> findEstadoSolicitudBySolicitudId(Long idSolicitud);

    public Estado findEstadoById(Long idEstado);

    public List<Estado> findAllEstados();
}
