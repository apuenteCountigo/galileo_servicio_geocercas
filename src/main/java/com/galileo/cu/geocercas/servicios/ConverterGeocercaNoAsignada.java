package com.galileo.cu.geocercas.servicios;

import com.galileo.cu.geocercas.entidades.Geocerca;
import com.galileo.cu.geocercas.utils.Utils;
import org.springframework.core.convert.converter.Converter;

import java.util.List;
import java.util.Map;

/**
 * Conversor de {@link Map} a {@link Geocerca}
 */
public class ConverterGeocercaNoAsignada implements Converter<List<Map<String, Object>>, Geocerca> {

    private static final Integer PARAM_ID = 14001;
    private static final Integer PARAM_NOMBRE = 14002;
    private static final Integer PARAM_DESCRIPCION = 14003;
    private static final Integer PARAM_AREA = 14004;

    private static final String FIELD_PARAMETER_ID = "ParameterID";
    private static final String FIELD_VALUE = "Value";

    @Override
    public Geocerca convert(List<Map<String, Object>> source) {
        Geocerca geocerca = new Geocerca();
        geocerca.setId(Utils.getValue(PARAM_ID, source));
        geocerca.setName(Utils.getValue(PARAM_NOMBRE, source));
        geocerca.setDescription(Utils.getValue(PARAM_DESCRIPCION, source));
        geocerca.setArea(Utils.getValue(PARAM_AREA, source));
        geocerca.setAsignada(false);

        return geocerca;
    }
}
