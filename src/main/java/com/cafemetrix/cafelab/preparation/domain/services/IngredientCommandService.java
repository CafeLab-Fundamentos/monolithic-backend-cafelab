package com.cafemetrix.cafelab.preparation.domain.services;

import com.cafemetrix.cafelab.preparation.domain.model.aggregates.Ingredient;
import com.cafemetrix.cafelab.preparation.domain.model.commands.CreateIngredientCommand;
import com.cafemetrix.cafelab.preparation.domain.model.commands.UpdateIngredientCommand;
import com.cafemetrix.cafelab.preparation.domain.model.commands.DeleteIngredientCommand;

import java.util.Optional;

public interface IngredientCommandService {
    Optional<Ingredient> handle(CreateIngredientCommand command);
    Optional<Ingredient> handle(UpdateIngredientCommand command);
    boolean handle(DeleteIngredientCommand command);
}
