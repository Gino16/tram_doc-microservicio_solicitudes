package com.tramite_documentario.microservicios.backend.microserviciosolicitudes.models.repository;

import com.tramite_documentario.microservicios.backend.microserviciosolicitudes.models.entity.PersonaSolicitud;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PersonaSolicitudRepository extends PagingAndSortingRepository<PersonaSolicitud, Long> {
    @Query("select ps from PersonaSolicitud ps join fetch ps.rolSolicitud r join fetch ps.persona join fetch ps.solicitud s where r.id=1 and s.id=?1")
    public PersonaSolicitud findPersonaSolicitudEmisorBySolicitud(Long idSolicitud);

    @Query("select ps from PersonaSolicitud ps join fetch ps.rolSolicitud r join fetch ps.persona p join ps.solicitud s where r.id=2 and s.id=?1")
    public List<PersonaSolicitud> findPersonaSolicitudReceptoresBySolicitud(Long idSolicitud);
}
