package com.cafemetrix.cafelab.management.domain.exceptions;

public class InventoryEntryNotFoundException extends RuntimeException {
    public InventoryEntryNotFoundException(Long inventoryEntryId) {
        super("Entrada de inventario no encontrada");
    }
}
