package com.cafemetrix.cafelab.preparation.domain.model.queries;

public record GetPortfoliosByUserIdQuery(Long userId) {
    public GetPortfoliosByUserIdQuery {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("userId es requerido");
        }
    }
}
