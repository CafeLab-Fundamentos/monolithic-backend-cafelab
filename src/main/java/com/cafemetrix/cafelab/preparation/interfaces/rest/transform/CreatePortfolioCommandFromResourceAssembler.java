package com.cafemetrix.cafelab.preparation.interfaces.rest.transform;

import com.cafemetrix.cafelab.preparation.domain.model.commands.CreatePortfolioCommand;
import com.cafemetrix.cafelab.preparation.interfaces.rest.dto.CreatePortfolioResource;

public class CreatePortfolioCommandFromResourceAssembler {
    public static CreatePortfolioCommand toCommand(Long userId, CreatePortfolioResource resource) {
        return new CreatePortfolioCommand(userId, resource.name());
    }
}
