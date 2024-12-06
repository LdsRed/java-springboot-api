package com.jlarcher.supermarketapi.exceptions;

public class ProductoExistenteException extends RuntimeException {

    public ProductoExistenteException(String message) {
        super(message);
    }
}
