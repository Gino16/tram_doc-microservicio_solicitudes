package com.tramite_documentario.microservicios.backend.microserviciosolicitudes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
@EnableAsync
@EntityScan({"com.tramite_documentario.microservicio.backend.commonpersonas.models.entity",
        "com.tramite_documentario.microservicios.backend.microserviciosolicitudes.models.entity"})
public class MicroservicioSolicitudesApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroservicioSolicitudesApplication.class, args);
    }

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.tramite_documentario.microservicios.backend.microserviciosolicitudes.controllers"))
                .paths(PathSelectors.any())
                .build();
    }
}
