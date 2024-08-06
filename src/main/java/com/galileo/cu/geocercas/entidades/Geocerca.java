package com.galileo.cu.geocercas.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.annotation.Transient;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Geocerca {
    private String index;
    private String id;
    private String name;
    private String description;
    private String area;
    @JsonIgnore
    private boolean asignada;
    private Boolean activa;
    private String bottomLeftLatitude;
    private String bottomLeftLongitude;
    private String topRightLatitude;
    private String topRightLongitude;
}
