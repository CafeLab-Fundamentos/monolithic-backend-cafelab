package com.cafemetrix.cafelab.production.domain.model.queries;

public record GetSuppliersByUserIdQuery(Long userId) {
    public GetSuppliersByUserIdQuery {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("userId es requerido y debe ser positivo");
        }
    }
}
