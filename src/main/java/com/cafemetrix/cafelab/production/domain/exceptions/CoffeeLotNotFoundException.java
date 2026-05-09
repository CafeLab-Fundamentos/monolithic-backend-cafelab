package com.cafemetrix.cafelab.production.domain.exceptions;

public class CoffeeLotNotFoundException extends RuntimeException {
    public CoffeeLotNotFoundException(Long coffeeLotId) {
        super("Lote de café no encontrado");
    }
}
