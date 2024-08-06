package com.galileo.cu.geocercas.utils;

import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Utils {

    public static final String NONAME = "NA";
    private static final String FIELD_PARAMETER_ID = "ParameterID";
    private static final String FIELD_VALUE = "Value";

    private Utils(){

    }

    public static String getValue(Integer parameterID, List<Map<String, Object>> celdas){
        if (CollectionUtils.isEmpty(celdas)){
            return null;
        }

        Optional<Map<String, Object>> result = celdas.stream()
                .filter(celda -> parameterID.equals((Integer)celda.get(FIELD_PARAMETER_ID)))
                .findFirst();

        if (result.isEmpty()){
            return null;
        }

        return (String)result.get().get(FIELD_VALUE);
    }
}
