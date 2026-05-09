package com.cafemetrix.cafelab.preparation.interfaces.rest.transform;

import com.cafemetrix.cafelab.preparation.domain.model.commands.UpdateRecipeCommand;
import com.cafemetrix.cafelab.preparation.interfaces.rest.dto.UpdateRecipeResource;

public class UpdateRecipeCommandFromResourceAssembler {
    public static UpdateRecipeCommand toCommandFromResource(
            Long userId, Long recipeId, UpdateRecipeResource resource) {
        return new UpdateRecipeCommand(
            userId,
            recipeId,
            resource.name(),
            resource.imageUrl(),
            resource.extractionMethod(),
            resource.extractionCategory(),
            resource.ratio(),
            resource.cuppingSessionId(),
            resource.portfolioId(),
            resource.preparationTime(),
            resource.steps(),
            resource.tips(),
            resource.cupping(),
            resource.grindSize()
        );
    }
}
