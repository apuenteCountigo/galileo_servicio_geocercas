package com.galileo.cu.geocercas.configuracion;

import com.galileo.cu.geocercas.entidades.BalizaRequest;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Objects;

public class BalizaRequestValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return BalizaRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "idDataminer", "idElement");

        BalizaRequest request = (BalizaRequest)target;
        if (Objects.isNull(request.getId())){
            errors.rejectValue("id", "Null value");
        }

        try {
            Integer i = Integer.parseInt(request.getIdDataminer());
        } catch (NumberFormatException nfe) {
            errors.rejectValue("idDataminer", "Not integer value");
        }

        try {
            Integer i = Integer.parseInt(request.getIdElement());
        } catch (NumberFormatException nfe) {
            errors.rejectValue("idElement", "Not integer value");
        }
    }
}
