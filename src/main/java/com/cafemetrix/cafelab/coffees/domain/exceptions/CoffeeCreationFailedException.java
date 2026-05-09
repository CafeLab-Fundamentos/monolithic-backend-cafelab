package com.cafemetrix.cafelab.coffees.domain.exceptions;

public class CoffeeCreationFailedException extends RuntimeException {
    public CoffeeCreationFailedException() {
        super("No se pudo crear el café");
    }
}
