package com.tramite_documentario.microservicios.backend.microserviciosolicitudes.services.personasolicitud;

import com.tramite_documentario.microservicios.backend.microserviciosolicitudes.models.entity.PersonaSolicitud;
import com.tramite_documentario.microservicios.backend.microserviciosolicitudes.models.repository.PersonaSolicitudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonaSolicitudServiceImpl implements PersonaSolicitudService{

    @Autowired
    private PersonaSolicitudRepository repository;

    @Override
    public PersonaSolicitud findPersonaSolicitudEmisorBySolicitud(Long idSolicitud) {
        return repository.findPersonaSolicitudEmisorBySolicitud(idSolicitud);
    }

    @Override
    public List<PersonaSolicitud> findPersonaSolicitudReceptoresBySolicitud(Long idSolicitud) {
        return repository.findPersonaSolicitudReceptoresBySolicitud(idSolicitud);
    }
}
