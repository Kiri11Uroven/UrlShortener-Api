package com.example.urlShortener.exeptionsHandler;

public class NoSuchUrlException extends RuntimeException {
    public NoSuchUrlException(String message) {
        super(message);
    }
}
