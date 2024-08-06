package com.galileo.cu.geocercas.controladores;

import com.galileo.cu.geocercas.configuracion.BalizaRequestValidator;
import com.galileo.cu.geocercas.servicios.GeocercaServicio;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

@SpringBootApplication
@Import({GlobalExceptionHandler.class})
public class GeocercaRestDocApplication {
    public static void main(String[] args) {
        SpringApplication.run(GeocercaRestDocApplication.class, args);
    }

    @Bean
    @Primary
    GeocercaServicio createGeocercaServicio(){
        return Mockito.mock(GeocercaServicio.class);
    }

    @Bean
    @Primary
    BalizaRequestValidator createBalizaRequestValidator(){
        return new BalizaRequestValidator();
    }

//    @Bean
//    @Primary
//    Geo
}
