package com.tramite_documentario.microservicios.backend.microserviciosolicitudes.controllers;

import com.tramite_documentario.microservicio.backend.commonpersonas.models.entity.Persona;
import com.tramite_documentario.microservicios.backend.microserviciosolicitudes.models.entity.*;
import com.tramite_documentario.microservicios.backend.microserviciosolicitudes.services.estadosolicitud.EstadoSolicitudService;
import com.tramite_documentario.microservicios.backend.microserviciosolicitudes.services.personasolicitud.PersonaSolicitudService;
import com.tramite_documentario.microservicios.backend.microserviciosolicitudes.services.solicitud.SolicitudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class SolicitudController {

    @Autowired
    private SolicitudService service;

    @Autowired
    private PersonaSolicitudService personaSolicitudService;

    @Autowired
    private EstadoSolicitudService estadoSolicitudService;

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Solicitud solicitud){
        Solicitud solicitudGuardada = service.save(solicitud);
        PersonaSolicitud personaSolicitudEmisor = new PersonaSolicitud();
        //Emisor
        personaSolicitudEmisor.setPersona(solicitud.getPersonaEmisor());
        personaSolicitudEmisor.setRol(new Rol(1L));
        personaSolicitudEmisor.setSolicitud(solicitudGuardada);

        //Receptor
        List<PersonaSolicitud> personaSolicitudReceptores = new ArrayList<>();
        solicitud.getPersonasReceptoras().forEach(persona -> {
            PersonaSolicitud personaSolicitudReceptor = new PersonaSolicitud();
            personaSolicitudReceptor.setPersona(persona);
            personaSolicitudReceptor.setRol(new Rol(2L));
            personaSolicitudReceptor.setSolicitud(solicitudGuardada);
            personaSolicitudReceptores.add(personaSolicitudReceptor);
        });

        //Guardando emisor y receptor
        personaSolicitudService.save(personaSolicitudEmisor);
        personaSolicitudService.saveAll(personaSolicitudReceptores);

        //Guardando primer estado, por default
        EstadoSolicitud estadoSolicitud = new EstadoSolicitud();
        estadoSolicitud.setSolicitud(solicitudGuardada);
        estadoSolicitud.setEstado(new Estado(1L));
        estadoSolicitud.setDescripcion("Primer Guardado");
        estadoSolicitud.setFecha(new Date());
        estadoSolicitudService.save(estadoSolicitud);

        return ResponseEntity.status(HttpStatus.CREATED).body(solicitudGuardada);
    }

    @GetMapping
    public ResponseEntity<?> listar() {
        List<Solicitud> solicitudes = (List<Solicitud>) service.findAll();
        //Iterando sobre cada solicitud encontrada para formar JSON
        solicitudes.forEach(solicitud -> {
            //Asignando la persona emisor a la solicitud
            solicitud.setPersonaEmisor(personaSolicitudService.findPersonaSolicitudEmisorBySolicitud(solicitud.getId()).getPersona());

            //Capturando las personas receptoras de cada solicitud en una lista de personasReceptoras
            List<Persona> personasReceptoras = personaSolicitudService.findPersonaSolicitudReceptoresBySolicitud(solicitud.getId()).stream().map(personaSolicitud -> {
                return personaSolicitud.getPersona();
            }).collect(Collectors.toList());
            //Estableciendo las personas receptoras de cada solicitud
            solicitud.setPersonasReceptoras(personasReceptoras);

            //Capturando los estados de cada solicitud
            List<EstadoSolicitud> estadoSolicitudes = estadoSolicitudService.findEstadoSolicitudBySolicitudId(solicitud.getId());
            //Asignando los estados de solicitud a cada solicitud
            solicitud.setEstadoSolicitudes(estadoSolicitudes);
        });
        return ResponseEntity.ok(solicitudes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> ver(@PathVariable Long id){
        Optional<Solicitud> s = service.findById(id);
        if (s.isEmpty()){
            return ResponseEntity.notFound().build();
        } else {
            Solicitud solicitud = s.get();
            solicitud.setPersonaEmisor(personaSolicitudService.findPersonaSolicitudEmisorBySolicitud(solicitud.getId()).getPersona());

            List<Persona> personasReceptoras = personaSolicitudService.findPersonaSolicitudReceptoresBySolicitud(solicitud.getId()).stream().map(personaSolicitud -> {
                return personaSolicitud.getPersona();
            }).collect(Collectors.toList());
            solicitud.setPersonasReceptoras(personasReceptoras);

            solicitud.setEstadoSolicitudes(estadoSolicitudService.findEstadoSolicitudBySolicitudId(solicitud.getId()));

            return ResponseEntity.ok(solicitud);
        }
    }

    @GetMapping("/tipo-solicitudes")
    public ResponseEntity<?> getTipoSolicitudes(){
        return ResponseEntity.ok(service.findAllTipoSolicitudes());
    }
}
