package com.example.urlShortener.exeptionsHandler;

public class UrlOverflowException extends RuntimeException {
    public UrlOverflowException(String message) {
        super(message);
    }
}
