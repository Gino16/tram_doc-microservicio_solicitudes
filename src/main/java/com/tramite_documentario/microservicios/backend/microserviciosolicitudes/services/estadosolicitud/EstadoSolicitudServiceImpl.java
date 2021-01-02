package com.tramite_documentario.microservicios.backend.microserviciosolicitudes.services.estadosolicitud;

import com.tramite_documentario.microservicio.backend.commonmicroservicios.services.CommonServiceImpl;
import com.tramite_documentario.microservicios.backend.microserviciosolicitudes.models.entity.EstadoSolicitud;
import com.tramite_documentario.microservicios.backend.microserviciosolicitudes.models.repository.EstadoSolicitudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadoSolicitudServiceImpl extends CommonServiceImpl<EstadoSolicitud, EstadoSolicitudRepository> implements EstadoSolicitudService{

    @Override
    public List<EstadoSolicitud> findEstadoSolicitudBySolicitudId(Long idSolicitud) {
        return repository.findEstadoSolicitudBySolicitudId(idSolicitud);
    }
}
