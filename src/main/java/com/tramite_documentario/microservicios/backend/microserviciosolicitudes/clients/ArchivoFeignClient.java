package com.tramite_documentario.microservicios.backend.microserviciosolicitudes.clients;

import com.tramite_documentario.microservicios.backend.commonarchivos.models.entity.Archivo;
import com.tramite_documentario.microservicios.backend.commonarchivos.models.entity.TipoArchivo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@FeignClient(name = "microservicio-archivos")
public interface ArchivoFeignClient {

    @GetMapping("/archivos-sin-solicitud")
    public List<Archivo> listarArchivosSinSolicitud();

    @PostMapping("/guardar-todos")
    public List<Archivo> guardarTodo(@RequestBody List<Archivo> archivos);

    @GetMapping("/solicitud/{id}")
    public List<Archivo> listarArchivosBySolicitud(@PathVariable Long id);

    @PostMapping
    public Archivo guardar( @RequestBody Archivo entity) ;

    @GetMapping("/tipoArchivos/{id}")
    public TipoArchivo verTipoArchivo(@PathVariable Long id);
}
