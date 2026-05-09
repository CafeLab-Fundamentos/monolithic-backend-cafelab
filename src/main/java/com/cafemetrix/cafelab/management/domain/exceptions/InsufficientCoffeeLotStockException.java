package com.cafemetrix.cafelab.management.domain.exceptions;

public class InsufficientCoffeeLotStockException extends RuntimeException {
    public InsufficientCoffeeLotStockException() {
        super("La cantidad supera el stock disponible del lote");
    }
}
