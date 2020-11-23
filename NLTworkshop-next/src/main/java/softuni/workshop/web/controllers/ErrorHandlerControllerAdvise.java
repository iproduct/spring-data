package softuni.workshop.web.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import softuni.workshop.excepion.CustomXmlException;
import softuni.workshop.excepion.EntityNotFoundException;

import java.util.List;

@ControllerAdvice
public class ErrorHandlerControllerAdvise {
    @ExceptionHandler
    public ModelAndView handleEntityNotFoundException(EntityNotFoundException ex) {
        return new ModelAndView("error", "errors", List.of(ex.getMessage()));
    }
    @ExceptionHandler
    public ModelAndView handleEntityNotFoundException(CustomXmlException ex) {
        return new ModelAndView("error", "errors", List.of(ex.getMessage()));
    }
}
