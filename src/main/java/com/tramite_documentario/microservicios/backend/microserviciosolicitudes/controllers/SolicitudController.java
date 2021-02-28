package com.tramite_documentario.microservicios.backend.microserviciosolicitudes.controllers;

import com.tramite_documentario.microservicio.backend.commonpersonas.models.entity.Persona;
import com.tramite_documentario.microservicios.backend.commonarchivos.models.entity.Archivo;
import com.tramite_documentario.microservicios.backend.commonarchivos.models.entity.TipoArchivo;
import com.tramite_documentario.microservicios.backend.microserviciosolicitudes.clients.ArchivoFeignClient;
import com.tramite_documentario.microservicios.backend.microserviciosolicitudes.models.entity.*;
import com.tramite_documentario.microservicios.backend.microserviciosolicitudes.services.estadosolicitud.EstadoSolicitudService;
import com.tramite_documentario.microservicios.backend.microserviciosolicitudes.services.personasolicitud.PersonaSolicitudService;
import com.tramite_documentario.microservicios.backend.microserviciosolicitudes.services.solicitud.SolicitudService;
import com.tramite_documentario.microservicios.backend.microserviciosolicitudes.view.pdf.SolicitudPdfView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

//CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, allowedHeaders = {"Content-Type"})
@RestController
public class SolicitudController {

    @Autowired
    private SolicitudService service;

    @Autowired
    private PersonaSolicitudService personaSolicitudService;

    @Autowired
    private EstadoSolicitudService estadoSolicitudService;

    @Autowired
    private ArchivoFeignClient archivoFeignClient;

    @PostMapping
    public ResponseEntity<?> guardar(@Valid @RequestBody Solicitud solicitud, BindingResult result) {

        if (result.hasErrors()) {
            return this.validar(result);
        }

        Solicitud solicitudGuardada = service.save(solicitud);
        PersonaSolicitud personaSolicitudEmisor = new PersonaSolicitud();
        //Emisor
        personaSolicitudEmisor.setPersona(solicitud.getPersonaEmisor());
        personaSolicitudEmisor.setRolSolicitud(new RolSolicitud(1L));
        personaSolicitudEmisor.setSolicitud(solicitudGuardada);

        //Receptor
        List<PersonaSolicitud> personaSolicitudReceptores = new ArrayList<>();
        solicitud.getPersonasReceptoras().forEach(persona -> {
            PersonaSolicitud personaSolicitudReceptor = new PersonaSolicitud();
            personaSolicitudReceptor.setPersona(persona);
            personaSolicitudReceptor.setRolSolicitud(new RolSolicitud(2L));
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

        //Obteniendo todos los archivos que se han registrado sin solicitud
        List<Archivo> archivosSinSolicitud = archivoFeignClient.listarArchivosSinSolicitud();

        //De la lista obtenida le agrego su id de solicitud a la que pertenecen
        List<Archivo> archivosParaGuardar = archivosSinSolicitud.stream().map(archivo -> {
            archivo.setIdSolicitud(solicitudGuardada.getId());
            return archivo;
        }).collect(Collectors.toList());

        //Guardo estas solicitudes asignadas con su idSolicitud para que se registren en microservicio archivos
        archivoFeignClient.guardarTodo(archivosParaGuardar);

        return ResponseEntity.status(HttpStatus.CREATED).body(solicitudGuardada);
    }

    @GetMapping
    public ResponseEntity<?> listar() {
        List<Solicitud> solicitudes = (List<Solicitud>) service.findAll();
        //Iterando sobre cada solicitud encontrada para formar JSON
        solicitudes.forEach(solicitud -> {
            setInfoArchivo(solicitud);

            //Capturando los estados de cada solicitud
            List<EstadoSolicitud> estadoSolicitudes = estadoSolicitudService.findEstadoSolicitudBySolicitudId(solicitud.getId());
            //Asignando los estados de solicitud a cada solicitud
            solicitud.setEstadoSolicitudes(estadoSolicitudes);
        });
        List<Solicitud> solicitudesFinal = new ArrayList<>();

        for (Solicitud solicitud: solicitudes){
            boolean esArchivado = false;
            for (EstadoSolicitud estadoSolicitud: solicitud.getEstadoSolicitudes()){
                if (estadoSolicitud.getEstado().getNombre().equals("Archivado")){
                    esArchivado = true;
                    break;
                }
            }
            if (!esArchivado){
                solicitudesFinal.add(solicitud);
            }
        }
        return ResponseEntity.ok(solicitudesFinal);
    }

    private void setInfoArchivo(Solicitud solicitud){
        //Asignando la persona emisor a la solicitud
        solicitud.setPersonaEmisor(personaSolicitudService.findPersonaSolicitudEmisorBySolicitud(solicitud.getId()).getPersona());

        //Capturando las personas receptoras de cada solicitud en una lista de personasReceptoras
        List<Persona> personasReceptoras = personaSolicitudService.findPersonaSolicitudReceptoresBySolicitud(solicitud.getId()).stream().map(personaSolicitud -> {
            return personaSolicitud.getPersona();
        }).collect(Collectors.toList());
        //Estableciendo las personas receptoras de cada solicitud
        solicitud.setPersonasReceptoras(personasReceptoras);

        //Capturando los archivos anexados en solicitud
        List<Archivo> archivos = archivoFeignClient.listarArchivosBySolicitud(solicitud.getId());

        solicitud.setArchivos(archivos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> ver(@PathVariable Long id) {
        Optional<Solicitud> s = service.findById(id);
        if (s.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            Solicitud solicitud = s.get();
            setInfoArchivo(solicitud);

            solicitud.setEstadoSolicitudes(estadoSolicitudService.findEstadoSolicitudBySolicitudId(solicitud.getId()));

            return ResponseEntity.ok(solicitud);
        }
    }

    @GetMapping("/tipo-solicitudes")
    public ResponseEntity<?> getTipoSolicitudes() {
        return ResponseEntity.ok(service.findAllTipoSolicitudes());
    }

    private ResponseEntity<?> validar(BindingResult result) {
        Map<String, Object> errores = new HashMap<>();
        result.getFieldErrors().forEach(fieldError -> {
            errores.put(fieldError.getField(), "El campo " + fieldError.getField() + " " + fieldError.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }

    @PutMapping("/actualizar-estado/{idSolicitud}/estado/{idEstado}")
    public ResponseEntity<?> actualizarEstadoSolicitud(@PathVariable Long idSolicitud,
                                                       @PathVariable Long idEstado,
                                                       HttpServletRequest request,
                                                       @RequestParam(required = false) MultipartFile documento) throws IOException {
        Long numVecesRepite;
        Optional<Solicitud> s = this.service.findById(idSolicitud);
        Optional<Estado> e = Optional.ofNullable(estadoSolicitudService.findEstadoById(idEstado));
        if (s.isEmpty() || e.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        Solicitud solicitud = s.get();
        Estado estado = e.get();
        List<EstadoSolicitud> estadoSolicitudes = solicitud.getEstadoSolicitudes();
        numVecesRepite = estadoSolicitudes.stream().filter(estadoSolicitud -> estadoSolicitud.getEstado().getId().equals(estado.getId())).count();
        boolean repetido = !(numVecesRepite == 0);

        if (repetido){
            return ResponseEntity.badRequest().body("Estado ya existe");
        }
        EstadoSolicitud estadoSolicitud = new EstadoSolicitud();
        estadoSolicitud.setSolicitud(solicitud);
        estadoSolicitud.setEstado(estado);
        String descripcion = request.getParameter("descripcion");
        estadoSolicitud.setDescripcion(descripcion);

        if (documento != null || !documento.isEmpty()){
            Archivo archivo = new Archivo();
            archivo.setFile(documento.getBytes());
            //Por defecto un numero de archivo que sera el tipo RESPUESTA
            TipoArchivo tipoArchivo = archivoFeignClient.verTipoArchivo(1L);
            archivo.setTipoArchivo(tipoArchivo);
            archivo.setDescripcion("RESPUESTA");
            archivo.setIdSolicitud(idSolicitud);
            archivoFeignClient.guardar(archivo);
        }
        solicitud.addEstadoSolicitud(estadoSolicitudService.save(estadoSolicitud));
        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(solicitud));

    }

    @GetMapping({"/exportar/{id}", "/exportar/{id}/"})
    public void exportarPDF(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Optional<Solicitud> s = this.service.findById(id);
        if (s.isEmpty()) {
            return;
        } else {
            Solicitud solicitud = s.get();
            setInfoArchivo(solicitud);
            response.setContentType("application/pdf");
            String headerKey = " ";
            String headerValue = "attachment; filename=" + solicitud.getTipoSolicitud().getNombre() + "_" + solicitud.getPersonaEmisor().getNombre() + ".pdf";
            response.setHeader(headerKey, headerValue);

            SolicitudPdfView solicitudPdfView = new SolicitudPdfView(solicitud);
            solicitudPdfView.export(response);
        }
    }
}
