package com.galileo.cu.geocercas.servicios;

import com.galileo.cu.geocercas.entidades.BalizaGeocerca;
import com.galileo.cu.geocercas.entidades.BalizaRequest;
import com.galileo.cu.geocercas.entidades.GeocercaEstado;
import org.springframework.http.ResponseEntity;

public interface GeocercaServicio {

    /**
     * Obtener las geocercas asignadas y no asignadas a una baliza
     *
     * @param request {@link BalizaRequest}
     * @return {@link BalizaGeocerca}
     */
    BalizaGeocerca getGeocercas(BalizaRequest request);

    /**
     * Agergar una geocerca
     *
     * @param elementId Identificador del elemento
     * @param dataminerId Identificador de Dataminer
     * @param index Indice de la geocerca
     */
    void addGeocerca(Integer elementId, Integer dataminerId, Integer index);

    void sendParameter(Integer dmaID, Integer elementID, Integer parameterID, Integer tableIndex, String parameterValue );

    ResponseEntity<?> getParameter(Integer dmaID, Integer elementID, Integer parameterID, Integer tableIndex, String parameterValue );

    GeocercaEstado getEstado(Integer dmaID, Integer elementID);
}
