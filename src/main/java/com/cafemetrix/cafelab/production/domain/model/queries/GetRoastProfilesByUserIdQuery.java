package com.cafemetrix.cafelab.production.domain.model.queries;

public record GetRoastProfilesByUserIdQuery(Long userId) {
    public GetRoastProfilesByUserIdQuery {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("userId es requerido y debe ser positivo");
        }
    }
}
