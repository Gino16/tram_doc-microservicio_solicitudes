package com.tramite_documentario.microservicios.backend.microserviciosolicitudes.clients;

import com.tramite_documentario.microservicios.backend.commonarchivos.models.entity.Archivo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "microservicio-archivos")
public interface ArchivoFeignClient {

    @GetMapping("/archivos-sin-solicitud")
    public List<Archivo> listarArchivosSinSolicitud();

    @PostMapping("/guardar-todos")
    public List<Archivo> guardarTodo(@RequestBody List<Archivo> archivos);

    @GetMapping("/solicitud/{id}")
    public List<Archivo> listarArchivosBySolicitud(@PathVariable Long id);
}
