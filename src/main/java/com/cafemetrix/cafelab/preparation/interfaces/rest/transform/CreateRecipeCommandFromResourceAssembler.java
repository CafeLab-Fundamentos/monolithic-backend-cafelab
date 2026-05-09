package com.cafemetrix.cafelab.preparation.interfaces.rest.transform;

import com.cafemetrix.cafelab.preparation.domain.model.commands.CreateRecipeCommand;
import com.cafemetrix.cafelab.preparation.interfaces.rest.dto.CreateRecipeResource;

public class CreateRecipeCommandFromResourceAssembler {

    public static CreateRecipeCommand toCommand(Long userId, CreateRecipeResource resource) {
        return new CreateRecipeCommand(
                userId,
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
                resource.grindSize());
    }
}
