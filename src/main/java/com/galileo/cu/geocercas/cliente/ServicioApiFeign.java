package com.galileo.cu.geocercas.cliente;

import com.galileo.cu.commons.models.DataMinerParameter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * Cliente para conexión con el microservicio ´servicio-api´ para acceder y actualizar los parámetros
 * en el DataMiner
 */
@FeignClient(name="servicio-apis")
public interface ServicioApiFeign {

    /**
     * Obtener las geocercas no asignadas
     *
     * @param elementId Identificador del elemento
     * @param dataminerId Identificador en el DataMiner
     * @return {@link ResponseEntity}
     */
    @GetMapping("/geocercas/{elementId}/{dataminerId}/noasignadas")
    ResponseEntity<Map<String, Object>> geoCercasNoAsiganadas(@PathVariable("elementId")Integer elementId, @PathVariable("dataminerId") Integer dataminerId);

    /**
     * Obtener las geocercas asignadas
     *
     * @param elementId Identificador del elemento
     * @param dataminerId Identificador en el DataMiner
     * @return {@link ResponseEntity}
     */
    @GetMapping("/geocercas/{elementId}/{dataminerId}/asignadas")
    ResponseEntity<Map<String, Object>> geoCercasAsiganadas(@PathVariable("elementId")Integer elementId, @PathVariable("dataminerId") Integer dataminerId);

    @PostMapping("/dataminer/parameter")
    ResponseEntity<?> setDataMinerParameter(@RequestBody DataMinerParameter parameter);

    @PostMapping("/dataminer/getparameter")
    ResponseEntity<?> getDataMinerParameter(@RequestBody DataMinerParameter parameter);
}
