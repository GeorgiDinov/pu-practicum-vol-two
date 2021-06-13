package com.pu.georgidinov.pupracticumvoltwo.api.v1.controlleradvise;

import com.pu.georgidinov.pupracticumvoltwo.exception.ResourceAlreadyExistsException;
import com.pu.georgidinov.pupracticumvoltwo.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handleResourceNotFoundException(ResourceNotFoundException exception) {
        return new ErrorMessage(logError(exception));
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorMessage handleResourceAlreadyExistsException(ResourceAlreadyExistsException exception) {
        return new ErrorMessage(logError(exception));
    }


    //== private methods ==
    private String logError(Exception exception) {
        String exceptionMessage = exception.getMessage();
        log.error("Handling {} with message = {}", exception.getClass().getSimpleName(), exceptionMessage);
        return exceptionMessage;
    }

}