package com.cafemetrix.cafelab.production.domain.exceptions;

public class CoffeeLotOwnershipException extends RuntimeException {
    public CoffeeLotOwnershipException() {
        super("El lote de café no pertenece al usuario indicado");
    }

    public CoffeeLotOwnershipException(String message) {
        super(message);
    }
}
