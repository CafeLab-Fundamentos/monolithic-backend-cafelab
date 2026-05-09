package com.cafemetrix.cafelab.preparation.application.internal.queryservices;

import com.cafemetrix.cafelab.preparation.domain.model.aggregates.Recipe;
import com.cafemetrix.cafelab.preparation.domain.model.queries.GetRecipeByIdForUserQuery;
import com.cafemetrix.cafelab.preparation.domain.model.queries.GetRecipesByUserIdQuery;
import com.cafemetrix.cafelab.preparation.domain.services.RecipeQueryService;
import com.cafemetrix.cafelab.preparation.infrastructure.persistence.jpa.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeQueryServiceImpl implements RecipeQueryService {
    private final RecipeRepository recipeRepository;

    public RecipeQueryServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public List<Recipe> handle(GetRecipesByUserIdQuery query) {
        return recipeRepository.findByUserIdOrderByCreatedAtDesc(query.userId());
    }

    @Override
    public Optional<Recipe> handle(GetRecipeByIdForUserQuery query) {
        return recipeRepository.findByIdAndUserId(query.recipeId(), query.userId());
    }
}
