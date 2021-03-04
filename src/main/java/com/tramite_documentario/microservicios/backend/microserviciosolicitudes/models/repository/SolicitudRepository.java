package com.tramite_documentario.microservicios.backend.microserviciosolicitudes.models.repository;

import com.tramite_documentario.microservicios.backend.microserviciosolicitudes.models.entity.Solicitud;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SolicitudRepository extends PagingAndSortingRepository<Solicitud, Long> {

    public Solicitud findByCodigoFirmaIsLike(String codigoFirma);
}
