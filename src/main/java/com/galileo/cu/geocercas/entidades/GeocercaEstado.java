package com.galileo.cu.geocercas.entidades;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GeocercaEstado {
    private Integer id;
    private String value;
    private String displayValue;
    private String lastValueChange;
    private Long lastValueChangeUTC;
}
