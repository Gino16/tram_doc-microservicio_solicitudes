package com.tramite_documentario.microservicios.backend.microserviciosolicitudes.models.repository;

import com.tramite_documentario.microservicios.backend.microserviciosolicitudes.models.entity.EstadoSolicitud;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface EstadoSolicitudRepository extends PagingAndSortingRepository<EstadoSolicitud, Long> {

    @Query("select es from EstadoSolicitud es join fetch es.solicitud s join fetch es.estado e where s.id = ?1")
    public List<EstadoSolicitud> findEstadoSolicitudBySolicitudId(Long idSolicitud);
}
