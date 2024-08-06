package com.galileo.cu.geocercas.servicios;

import com.galileo.cu.commons.models.DataMinerParameter;
import com.galileo.cu.geocercas.cliente.ServicioApiFeign;
import com.galileo.cu.geocercas.entidades.BalizaGeocerca;
import com.galileo.cu.geocercas.entidades.BalizaRequest;
import com.galileo.cu.geocercas.entidades.Geocerca;
import com.galileo.cu.geocercas.entidades.GeocercaEstado;
import com.galileo.cu.geocercas.exceptions.GeocercaLimitException;
import com.galileo.cu.geocercas.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GeocercaServicioImpl implements GeocercaServicio{

    private final ServicioApiFeign servicioApiFeign;
    private final ConverterGeocercaAsignada converterGeocercaAsignada;
    private final ConverterGeocercaNoAsignada converterGeocercaNoAsignada;
    private final ConverterGeocercaEstado converterGeocercaEstado;

    private static final String FILED_DATA = "d";
    private static final String FILED_INDEX = "IndexValue";
    private static final String FILED_CELLS = "Cells";
    private static final int MAX_ASIGANDAS = 10;


    @Autowired
    public GeocercaServicioImpl(ConverterGeocercaAsignada geocercaAsignada,
                                ConverterGeocercaNoAsignada geocercaNoAsignada,
                                ConverterGeocercaEstado geocercaEstado,
                                ServicioApiFeign apiFeign){
        converterGeocercaAsignada = geocercaAsignada;
        converterGeocercaNoAsignada = geocercaNoAsignada;
        converterGeocercaEstado = geocercaEstado;
        servicioApiFeign = apiFeign;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BalizaGeocerca getGeocercas(BalizaRequest request) {

        BalizaGeocerca result = new BalizaGeocerca();

        Integer elementId = Integer.parseInt(request.getIdElement());
        Integer dataminerId = Integer.parseInt(request.getIdDataminer());

        result.setIdDataminer(request.getIdDataminer());
        result.setIdElement(request.getIdElement());

        ResponseEntity<Map<String, Object>> resAsignadas = servicioApiFeign.geoCercasAsiganadas(elementId, dataminerId);
        if (resAsignadas.hasBody() && CollectionUtils.isEmpty((List) resAsignadas.getBody().get(FILED_DATA))) {
            result.setAsignadas(Collections.EMPTY_LIST);
        }else{
            result.setAsignadas(buildGeocercas(converterGeocercaAsignada, (List<Map<String, Object>>) resAsignadas.getBody().get(FILED_DATA)));
        }
        ResponseEntity<Map<String, Object>> resNoAsignadas = servicioApiFeign.geoCercasNoAsiganadas(elementId, dataminerId);

        if (resNoAsignadas.hasBody() && CollectionUtils.isEmpty((List) resNoAsignadas.getBody().get(FILED_DATA))) {
            result.setNoAsignadas(Collections.EMPTY_LIST);
        }else{
            result.setNoAsignadas(buildGeocercas(converterGeocercaNoAsignada, (List<Map<String, Object>>) resNoAsignadas.getBody().get(FILED_DATA)));
        }
        return result;
    }

    @Override
    public void addGeocerca(Integer elementId, Integer dataminerId, Integer index) {
        ResponseEntity<Map<String, Object>> resAsignadas = servicioApiFeign.geoCercasAsiganadas(elementId, dataminerId);
        if (resAsignadas.hasBody()
                && !CollectionUtils.isEmpty((List) resAsignadas.getBody().get(FILED_DATA))
                && contarAsignadas((List) resAsignadas.getBody().get(FILED_DATA)) >= MAX_ASIGANDAS) {
            throw new GeocercaLimitException("Solo se pueden asignar 10 geocercas");
        }

        sendParameter(dataminerId, elementId, 14006, index, "1");

    }

    private int contarAsignadas(List<Map<String, Object>> lista){
        if (CollectionUtils.isEmpty(lista)){
            return 0;
        }
        List<Geocerca> asignadas = buildGeocercas(converterGeocercaAsignada, lista);
        return asignadas.stream()
                .filter(geocerca -> !Utils.NONAME.equals(geocerca.getName()))
                .mapToInt(geocerca -> 1)
                .sum();
    }

    @Override
    public void sendParameter(Integer dmaID, Integer elementID, Integer parameterID, Integer tableIndex, String parameterValue) {
        DataMinerParameter parameter = new DataMinerParameter();
        parameter.setDmaID(dmaID);
        parameter.setElementID(elementID);
        parameter.setParameterID(parameterID);
        parameter.setTableIndex(tableIndex);
        parameter.setParameterValue(parameterValue);

        servicioApiFeign.setDataMinerParameter(parameter);
    }

    @Override
    public ResponseEntity<?> getParameter(Integer dmaID, Integer elementID, Integer parameterID, Integer tableIndex, String parameterValue) {
        DataMinerParameter parameter = new DataMinerParameter();
        parameter.setDmaID(dmaID);
        parameter.setElementID(elementID);
        parameter.setParameterID(parameterID);
        parameter.setTableIndex(tableIndex);
        parameter.setParameterValue(parameterValue);

        return servicioApiFeign.getDataMinerParameter(parameter);
    }

    @Override
    public GeocercaEstado getEstado(Integer dmaID, Integer elementID) {
        ResponseEntity<Map<String, Object>> response = (ResponseEntity<Map<String, Object>>)getParameter(dmaID, elementID, 5964, null, null);

        if (response.hasBody()
                && CollectionUtils.isEmpty((Map) response.getBody().get(FILED_DATA))) {
            return new GeocercaEstado();
        }

        return converterGeocercaEstado.convert((Map<String, Object>) response.getBody().get(FILED_DATA));
    }

    private List<Geocerca> buildGeocercas(Converter<List<Map<String, Object>>, Geocerca> converter, List<Map<String, Object>> data){
        return data.stream()
                .map(item -> {
                    List<Map<String, Object>> cells = (List<Map<String, Object>>) item.get(FILED_CELLS);
                    Geocerca geocerca = converter.convert(cells);
                    geocerca.setIndex((String) item.get(FILED_INDEX));
                    return geocerca;
                })
                .collect(Collectors.toList());
    }
}
