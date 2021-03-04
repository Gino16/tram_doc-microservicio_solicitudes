package com.tramite_documentario.microservicios.backend.microserviciosolicitudes.services.solicitud;

import com.tramite_documentario.microservicio.backend.commonmicroservicios.services.CommonServiceImpl;
import com.tramite_documentario.microservicio.backend.commonpersonas.models.entity.Persona;
import com.tramite_documentario.microservicios.backend.microserviciosolicitudes.models.entity.Solicitud;
import com.tramite_documentario.microservicios.backend.microserviciosolicitudes.models.entity.TipoSolicitud;
import com.tramite_documentario.microservicios.backend.microserviciosolicitudes.models.repository.SolicitudRepository;
import com.tramite_documentario.microservicios.backend.microserviciosolicitudes.models.repository.TipoSolicitudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

@Service
public class SolicitudServiceImpl extends CommonServiceImpl<Solicitud, SolicitudRepository> implements SolicitudService {


    @Autowired
    private TipoSolicitudRepository tipoSolicitudRepository;

    @Autowired
    private JavaMailSender mailSender;
    @Override
    public List<TipoSolicitud> findAllTipoSolicitudes(){
        return (List<TipoSolicitud>) tipoSolicitudRepository.findAll();
    }

    @Override
    public void sendFiles(Persona persona, String codigo) throws MessagingException, UnsupportedEncodingException {
        String[] emailsToSend = {persona.getCorreo()};




        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom("ginoag7@gmail.com", "Mikasa Support");
        helper.setTo(emailsToSend);

        String mensaje = "Envio de codigo para firma digital";

        String contenido = "<h3>Hola " + persona.getNombre() + " " + persona.getApellidos() + "</h3>" +
                "<p>Se le alcanza el siguiente codigo para firma digital, digitelo en el sistema</p>" +
                "<h1>" + codigo + "</h1>";

        helper.setSubject(mensaje);
        helper.setText(contenido, true);

        mailSender.send(message);
    }

    @Override
    public Solicitud findByCodigoFirmaIsLike(String codigoFirma) {
        return this.repository.findByCodigoFirmaIsLike(codigoFirma);
    }
}
