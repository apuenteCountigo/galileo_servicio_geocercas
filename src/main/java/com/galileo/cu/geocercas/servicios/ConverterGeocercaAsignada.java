package com.galileo.cu.geocercas.servicios;

import com.galileo.cu.geocercas.entidades.Geocerca;
import com.galileo.cu.geocercas.utils.Utils;
import org.springframework.core.convert.converter.Converter;

import java.util.List;
import java.util.Map;

/**
 * Conversor de {@link java.util.Map} a {@link com.galileo.cu.geocercas.entidades.Geocerca}
 */
public class ConverterGeocercaAsignada implements Converter<List<Map<String, Object>>, Geocerca> {

    private static final Integer PARAM_ID = 13001;
    private static final Integer PARAM_ESTADO = 13008;
    private static final Integer PARAM_NOMBRE = 13002;
    private static final Integer PARAM_BOTTOM_LEFT_LAT = 13004;
    private static final Integer PARAM_BOTTOM_LEFT_LONG = 13005;
    private static final Integer PARAM_TOP_RIGHT_LAT = 13006;
    private static final Integer PARAM_TOP_RIGHT_LONG = 13007;

    private static final String FIELD_PARAMETER_ID = "ParameterID";
    private static final String FIELD_VALUE = "Value";

    private static final String ACTIVO = "1";


    @Override
    public Geocerca convert(List<Map<String, Object>> source) {
        Geocerca geocerca = new Geocerca();
        geocerca.setId(Utils.getValue(PARAM_ID, source));
        geocerca.setName(Utils.getValue(PARAM_NOMBRE, source));
        geocerca.setBottomLeftLatitude(Utils.getValue(PARAM_BOTTOM_LEFT_LAT, source));
        geocerca.setBottomLeftLongitude(Utils.getValue(PARAM_BOTTOM_LEFT_LONG, source));
        geocerca.setTopRightLatitude(Utils.getValue(PARAM_TOP_RIGHT_LAT, source));
        geocerca.setTopRightLongitude(Utils.getValue(PARAM_TOP_RIGHT_LONG, source));
        geocerca.setAsignada(true);

        String activo = Utils.getValue(PARAM_ESTADO, source);
        geocerca.setActiva(ACTIVO.equals(activo));

        return geocerca;
    }
}
