package com.ufv.dis.exception;

/** Se lanza cuando un recurso solicitado no existe. Se traduce en un 404. */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
