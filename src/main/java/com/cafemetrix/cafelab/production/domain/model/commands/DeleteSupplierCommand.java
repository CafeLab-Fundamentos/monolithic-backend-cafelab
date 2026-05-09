package com.cafemetrix.cafelab.production.domain.model.commands;

public record DeleteSupplierCommand(Long supplierId) {
    public DeleteSupplierCommand {
        if (supplierId == null || supplierId <= 0) throw new IllegalArgumentException("SupplierId es requerido y debe ser positivo");
    }
}
