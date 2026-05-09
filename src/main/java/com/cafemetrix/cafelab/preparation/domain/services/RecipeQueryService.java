package com.cafemetrix.cafelab.preparation.domain.services;

import com.cafemetrix.cafelab.preparation.domain.model.aggregates.Recipe;
import com.cafemetrix.cafelab.preparation.domain.model.queries.GetRecipeByIdForUserQuery;
import com.cafemetrix.cafelab.preparation.domain.model.queries.GetRecipesByUserIdQuery;

import java.util.List;
import java.util.Optional;

public interface RecipeQueryService {
    List<Recipe> handle(GetRecipesByUserIdQuery query);

    Optional<Recipe> handle(GetRecipeByIdForUserQuery query);
}
