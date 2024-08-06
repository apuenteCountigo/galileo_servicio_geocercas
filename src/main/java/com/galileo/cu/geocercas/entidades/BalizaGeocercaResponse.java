package com.galileo.cu.geocercas.entidades;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.hateoas.EntityModel;

import java.util.List;
import java.util.stream.Collectors;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BalizaGeocercaResponse {
    private String idDataminer;
    private String idElement;
    private List<EntityModel<Geocerca>> noAsignadas;
    private List<EntityModel<Geocerca>> asignadas;

    public BalizaGeocercaResponse(){

    }
    public BalizaGeocercaResponse(BalizaGeocerca geocerca){
        idDataminer = geocerca.getIdDataminer();
        idElement = geocerca.getIdElement();

        GeocercaModelAssembler assembler = new GeocercaModelAssembler(idDataminer, idElement);
        asignadas = geocerca.getAsignadas().stream()
                .map(item -> assembler.toModel(item))
                .collect(Collectors.toList());

        noAsignadas = geocerca.getNoAsignadas().stream()
                .map(item -> assembler.toModel(item))
                .collect(Collectors.toList());
    }
}
