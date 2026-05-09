package com.cafemetrix.cafelab.preparation.interfaces.rest.transform;

import com.cafemetrix.cafelab.preparation.domain.model.commands.CreateIngredientCommand;
import com.cafemetrix.cafelab.preparation.interfaces.rest.dto.CreateIngredientResource;

public class CreateIngredientCommandFromResourceAssembler {
    public static CreateIngredientCommand toCommandFromResource(CreateIngredientResource resource) {
        return new CreateIngredientCommand(
            resource.recipeId(),
            resource.name(),
            resource.amount(),
            resource.unit()
        );
    }
}
