package com.portafolio.my_portafolio_backend.exception.handler;


import com.portafolio.my_portafolio_backend.exception.Validationexception;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice//anotacion especial que son para clases con logica de manejo de excepciones, centralizando la logica de errores en un solo lugar
public class GlobalExceptionHandler {


    @ExceptionHandler(Validationexception.class)//apunta las variables a la vista
    public String handleValidatorException(Validationexception ex, Model model){//trabaja con vistas, retorna vistas
        model.addAttribute("errors",ex.getBindingResult().getAllErrors());
        model.addAttribute("message", "Se encontraron errores de validacion");

        return "error/validation";

    }
}
