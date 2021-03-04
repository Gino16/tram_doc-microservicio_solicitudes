package com.tramite_documentario.microservicios.backend.microserviciosolicitudes.services.solicitud;

import com.tramite_documentario.microservicio.backend.commonmicroservicios.services.CommonService;
import com.tramite_documentario.microservicio.backend.commonpersonas.models.entity.Persona;
import com.tramite_documentario.microservicios.backend.microserviciosolicitudes.models.entity.Solicitud;
import com.tramite_documentario.microservicios.backend.microserviciosolicitudes.models.entity.TipoSolicitud;
import org.springframework.scheduling.annotation.Async;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

public interface SolicitudService extends CommonService<Solicitud> {


    public List<TipoSolicitud> findAllTipoSolicitudes();

    @Async
    public void sendFiles(Persona persona, String codigo) throws MessagingException, UnsupportedEncodingException;

    public Solicitud findByCodigoFirmaIsLike(String codigoFirma);
}
