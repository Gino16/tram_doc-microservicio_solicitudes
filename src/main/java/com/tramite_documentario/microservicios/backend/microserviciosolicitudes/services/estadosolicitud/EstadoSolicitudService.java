package com.tramite_documentario.microservicios.backend.microserviciosolicitudes.services.estadosolicitud;

import com.tramite_documentario.microservicios.backend.microserviciosolicitudes.models.entity.EstadoSolicitud;

import java.util.List;

public interface EstadoSolicitudService {
    public List<EstadoSolicitud> findEstadoSolicitudBySolicitudId(Long idSolicitud);
}
