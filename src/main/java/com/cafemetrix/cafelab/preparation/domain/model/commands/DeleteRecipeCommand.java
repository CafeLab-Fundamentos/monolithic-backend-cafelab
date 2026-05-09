package com.cafemetrix.cafelab.preparation.domain.model.commands;

public record DeleteRecipeCommand(Long recipeId, Long userId) {
    public DeleteRecipeCommand {
        if (recipeId == null || recipeId <= 0) throw new IllegalArgumentException("RecipeId es requerido y debe ser positivo");
        if (userId == null || userId <= 0) throw new IllegalArgumentException("userId es requerido y debe ser positivo");
    }
}
