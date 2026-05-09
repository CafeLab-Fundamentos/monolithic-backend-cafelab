package com.cafemetrix.cafelab.management.domain.model.commands;

public record DeleteInventoryEntryCommand(Long inventoryEntryId) {
    public DeleteInventoryEntryCommand {
        if (inventoryEntryId == null || inventoryEntryId <= 0) throw new IllegalArgumentException("InventoryEntryId es requerido y debe ser positivo");
    }
}
