package com.galileo.cu.geocercas.controladores;

import com.galileo.cu.geocercas.configuracion.BalizaRequestValidator;
import com.galileo.cu.geocercas.entidades.BalizaGeocercaResponse;
import com.galileo.cu.geocercas.entidades.BalizaRequest;
import com.galileo.cu.geocercas.servicios.GeocercaServicio;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@Log4j2
public class GeocercaController{

    private final GeocercaServicio servicio;
    private final BalizaRequestValidator balizaRequestValidator;

    @Autowired
    public GeocercaController(GeocercaServicio servicio, BalizaRequestValidator balizaRequestValidator){
        this.servicio = servicio;
        this.balizaRequestValidator = balizaRequestValidator;
        log.info("Creando controlador");
    }

    @InitBinder
    private void initBinder(WebDataBinder binder){
        binder.addValidators(balizaRequestValidator);
    }

    @PostMapping
    public ResponseEntity<EntityModel<BalizaGeocercaResponse>> getGeocercas(@Validated @RequestBody BalizaRequest request){

        BalizaGeocercaResponse response = new BalizaGeocercaResponse(servicio.getGeocercas(request));

        EntityModel<BalizaGeocercaResponse> model = EntityModel.of(response);
        model.add(
            linkTo(methodOn(GeocercaController.class).getGeocercas(request)).withSelfRel().withType("POST")
        );

        return ResponseEntity.ok(model);
    }

    @PostMapping("/{elementId}/{dataminerId}/{index}")
    public ResponseEntity<?> addGeocerca(@PathVariable("elementId") Integer elementId,
                                         @PathVariable("dataminerId") Integer dataminerId,
                                         @PathVariable("index") Integer index){
        servicio.addGeocerca(elementId, dataminerId, index);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{elementId}/{dataminerId}/{index}/activar")
    public ResponseEntity<?> activarGeocerca(@PathVariable("elementId") Integer elementId,
                                         @PathVariable("dataminerId") Integer dataminerId,
                                         @PathVariable("index") Integer index){
        servicio.sendParameter(dataminerId, elementId, 13010, index, "0");
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{elementId}/{dataminerId}/{index}/desactivar")
    public ResponseEntity<?> desactivarGeocerca(@PathVariable("elementId") Integer elementId,
                                             @PathVariable("dataminerId") Integer dataminerId,
                                             @PathVariable("index") Integer index){
        servicio.sendParameter(dataminerId, elementId, 13012, index, "0");
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{elementId}/{dataminerId}/{index}")
    public ResponseEntity<?> deleteGeocerca(@PathVariable("elementId") Integer elementId,
                                         @PathVariable("dataminerId") Integer dataminerId,
                                         @PathVariable("index") Integer index){
        servicio.sendParameter(dataminerId, elementId, 13009, index, "1");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{elementId}/{dataminerId}/estado")
    public ResponseEntity<?> getGeocercaEstado(@PathVariable("elementId") Integer elementId,
                                            @PathVariable("dataminerId") Integer dataminerId){
        return ResponseEntity.ok(servicio.getEstado(dataminerId, elementId));
    }

}
