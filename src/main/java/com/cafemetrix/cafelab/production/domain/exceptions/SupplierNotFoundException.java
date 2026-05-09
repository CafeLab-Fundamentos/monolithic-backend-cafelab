package com.cafemetrix.cafelab.production.domain.exceptions;

public class SupplierNotFoundException extends RuntimeException {
    public SupplierNotFoundException(Long supplierId) {
        super("Proveedor no encontrado");
    }
}
