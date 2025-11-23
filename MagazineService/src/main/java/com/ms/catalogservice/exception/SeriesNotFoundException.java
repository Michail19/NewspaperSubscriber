package com.ms.catalogservice.exception;

public class SeriesNotFoundException extends RuntimeException {
    public SeriesNotFoundException(String message) {
        super(message);
    }
}
