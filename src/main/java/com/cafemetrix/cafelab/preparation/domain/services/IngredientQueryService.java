package com.cafemetrix.cafelab.preparation.domain.services;

import com.cafemetrix.cafelab.preparation.domain.model.aggregates.Ingredient;
import com.cafemetrix.cafelab.preparation.domain.model.queries.GetIngredientsByRecipeIdQuery;

import java.util.List;

public interface IngredientQueryService {
    List<Ingredient> handle(GetIngredientsByRecipeIdQuery query);
}
