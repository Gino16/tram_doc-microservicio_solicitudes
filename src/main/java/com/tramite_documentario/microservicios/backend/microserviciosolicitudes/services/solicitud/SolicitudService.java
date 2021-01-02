package com.tramite_documentario.microservicios.backend.microserviciosolicitudes.services.solicitud;

import com.tramite_documentario.microservicios.backend.microserviciosolicitudes.models.entity.Solicitud;

import java.util.List;
import java.util.Optional;

public interface SolicitudService{

    public List<Solicitud> findAll();

    public Optional<Solicitud> findById(Long id);
}
