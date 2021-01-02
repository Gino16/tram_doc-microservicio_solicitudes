package com.tramite_documentario.microservicios.backend.microserviciosolicitudes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.tramite_documentario.microservicio.backend.commonpersonas.models.entity",
        "com.tramite_documentario.microservicios.backend.microserviciosolicitudes.models.entity"})
public class MicroservicioSolicitudesApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroservicioSolicitudesApplication.class, args);
    }

}
