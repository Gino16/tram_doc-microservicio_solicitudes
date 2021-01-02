package com.tramite_documentario.microservicios.backend.microserviciosolicitudes.services.solicitud;

import com.tramite_documentario.microservicio.backend.commonmicroservicios.services.CommonServiceImpl;
import com.tramite_documentario.microservicios.backend.microserviciosolicitudes.models.entity.Solicitud;
import com.tramite_documentario.microservicios.backend.microserviciosolicitudes.models.entity.TipoSolicitud;
import com.tramite_documentario.microservicios.backend.microserviciosolicitudes.models.repository.SolicitudRepository;
import com.tramite_documentario.microservicios.backend.microserviciosolicitudes.models.repository.TipoSolicitudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SolicitudServiceImpl extends CommonServiceImpl<Solicitud, SolicitudRepository> implements SolicitudService {


    @Autowired
    private TipoSolicitudRepository tipoSolicitudRepository;

    @Override
    public List<TipoSolicitud> findAllTipoSolicitudes(){
        return (List<TipoSolicitud>) tipoSolicitudRepository.findAll();
    }
}
