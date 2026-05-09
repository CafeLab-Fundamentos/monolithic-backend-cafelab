package com.cafemetrix.cafelab.preparation.domain.model.queries;

public record GetRecipesByUserIdQuery(Long userId) {
    public GetRecipesByUserIdQuery {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("userId es requerido");
        }
    }
}
