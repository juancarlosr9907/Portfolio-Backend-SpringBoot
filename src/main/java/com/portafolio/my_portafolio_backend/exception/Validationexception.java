package com.portafolio.my_portafolio_backend.exception;

import lombok.Getter;
import org.springframework.validation.BindingResult;

@Getter
public class Validationexception extends RuntimeException {

    private final BindingResult bindingResult;
    public Validationexception(BindingResult bindingResult) {
        super("Error en la validación: Se encuentraron "+bindingResult.getErrorCount());
        this.bindingResult = bindingResult;
    }

}
