package com.galileo.cu.geocercas.configuracion;

import com.galileo.cu.geocercas.servicios.ConverterGeocercaAsignada;
import com.galileo.cu.geocercas.servicios.ConverterGeocercaEstado;
import com.galileo.cu.geocercas.servicios.ConverterGeocercaNoAsignada;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Configuracion {

    @Bean
    public ConverterGeocercaAsignada createGeocercaAsignadaConverter(){
        return new ConverterGeocercaAsignada();
    }

    @Bean
    public ConverterGeocercaNoAsignada createGeocercaNoAsignadaConverter(){
        return new ConverterGeocercaNoAsignada();
    }

    @Bean
    public BalizaRequestValidator createBalizaRequestValidator(){
        return new BalizaRequestValidator();
    }

    @Bean
    public ConverterGeocercaEstado createConverterGeocercaEstado(){
        return new ConverterGeocercaEstado();
    }

}
