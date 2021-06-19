package com.pu.georgidinov.pupracticumvoltwo.exception;

public class ResourceAlreadyExistsException extends RuntimeException {

    public ResourceAlreadyExistsException() {
    }

    public ResourceAlreadyExistsException(String message) {
        super(message);
    }
}
