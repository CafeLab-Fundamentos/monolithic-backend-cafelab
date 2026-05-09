package com.cafemetrix.cafelab.preparation.interfaces.rest.transform;

import com.cafemetrix.cafelab.preparation.domain.model.commands.UpdateIngredientCommand;
import com.cafemetrix.cafelab.preparation.interfaces.rest.dto.UpdateIngredientResource;

public class UpdateIngredientCommandFromResourceAssembler {
    public static UpdateIngredientCommand toCommandFromResource(Long ingredientId, UpdateIngredientResource resource) {
        return new UpdateIngredientCommand(
            ingredientId,
            resource.name(),
            resource.amount(),
            resource.unit()
        );
    }
}
