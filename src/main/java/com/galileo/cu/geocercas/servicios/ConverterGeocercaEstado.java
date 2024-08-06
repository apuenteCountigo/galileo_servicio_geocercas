package com.galileo.cu.geocercas.servicios;

import com.galileo.cu.geocercas.entidades.Geocerca;
import com.galileo.cu.geocercas.entidades.GeocercaEstado;
import org.springframework.core.convert.converter.Converter;

import java.util.Map;

/**
 * Conversor de {@link Map} a {@link GeocercaEstado}
 */
public class ConverterGeocercaEstado implements Converter<Map<String, Object>, GeocercaEstado> {
    private static final String FIELD_ID = "ID";
    private static final String FIELD_DISPLAY_VALUE = "DisplayValue";
    private static final String FIELD_VALUE = "Value";
    private static final String FIELD_LASTVALUE_CHANGE = "LastValueChange";
    private static final String FIELD_LASTVALUE_CHANGEUTC = "LastValueChangeUTC";


    @Override
    public GeocercaEstado convert(Map<String, Object> source) {
        GeocercaEstado estado = new GeocercaEstado();
        estado.setId((Integer)source.get(FIELD_ID));
        estado.setDisplayValue((String)source.get(FIELD_DISPLAY_VALUE));
        estado.setValue((String)source.get(FIELD_VALUE));
        estado.setLastValueChange((String)source.get(FIELD_LASTVALUE_CHANGE));
        estado.setLastValueChangeUTC((Long) source.get(FIELD_LASTVALUE_CHANGEUTC));

        return estado;
    }
}
