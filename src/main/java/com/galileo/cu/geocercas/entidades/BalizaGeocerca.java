package com.galileo.cu.geocercas.entidades;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.hateoas.EntityModel;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BalizaGeocerca {
    private String idDataminer;
    private String idElement;
    private List<Geocerca> noAsignadas;
    private List<Geocerca> asignadas;
}
