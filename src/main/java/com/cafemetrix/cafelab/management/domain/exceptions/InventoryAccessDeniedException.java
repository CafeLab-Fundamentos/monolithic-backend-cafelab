package com.cafemetrix.cafelab.management.domain.exceptions;

public class InventoryAccessDeniedException extends RuntimeException {
    public InventoryAccessDeniedException(String message) {
        super(message);
    }
}
