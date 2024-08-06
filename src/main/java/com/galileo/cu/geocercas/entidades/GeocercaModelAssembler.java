package com.galileo.cu.geocercas.entidades;

import com.galileo.cu.geocercas.controladores.GeocercaController;
import com.galileo.cu.geocercas.utils.Utils;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class GeocercaModelAssembler implements SimpleRepresentationModelAssembler<Geocerca> {

    private final Integer idDataminer;
    private final Integer idElement;

    public GeocercaModelAssembler(String idDataminer, String idElement){
        this.idDataminer = Integer.parseInt(idDataminer);
        this.idElement = Integer.parseInt(idElement);
    }

    @Override
    public void addLinks(EntityModel<Geocerca> model) {

        if (Utils.NONAME.equals(model.getContent().getName())){
            return;
        }

        if (model.getContent().isAsignada()){
            if (model.getContent().getActiva()){
                model.add(
                    linkTo(methodOn(GeocercaController.class).desactivarGeocerca(idElement, idDataminer, Integer.parseInt(model.getContent().getIndex()))).withRel("desactivar-geocerca").withType("PUT")
                );
            }else{
                model.add(
                    linkTo(methodOn(GeocercaController.class).activarGeocerca(idElement, idDataminer, Integer.parseInt(model.getContent().getIndex()))).withRel("activar-geocerca").withType("PUT")
                );
            }
            model.add(
                linkTo(methodOn(GeocercaController.class).deleteGeocerca(idElement, idDataminer, Integer.parseInt(model.getContent().getIndex()))).withRel("delete-geocerca").withType("DELETE")
            );
        }else{
            model.add(
                    linkTo(methodOn(GeocercaController.class).addGeocerca(idElement, idDataminer, Integer.parseInt(model.getContent().getIndex()))).withRel("add-geocerca").withType("POST")
            );
        }
    }

    @Override
    public void addLinks(CollectionModel<EntityModel<Geocerca>> resources) {

    }
}
