package com.tramite_documentario.microservicios.backend.microserviciosolicitudes.models.repository;

import com.tramite_documentario.microservicios.backend.microserviciosolicitudes.models.entity.TipoSolicitud;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TipoSolicitudRepository extends PagingAndSortingRepository<TipoSolicitud, Long> {
}
