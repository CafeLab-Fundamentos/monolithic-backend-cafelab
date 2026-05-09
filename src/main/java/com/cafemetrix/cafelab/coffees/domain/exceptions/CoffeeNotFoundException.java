package com.cafemetrix.cafelab.coffees.domain.exceptions;

public class CoffeeNotFoundException extends RuntimeException {
    public CoffeeNotFoundException(Long coffeeId) {
        super("Café no encontrado");
    }
}
