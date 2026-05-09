package com.cafemetrix.cafelab.preparation.interfaces.rest.dto;

public record IngredientResource(
    Long id,
    Long recipeId,
    String name,
    Double amount,
    String unit
) {}
