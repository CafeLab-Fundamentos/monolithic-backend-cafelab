package com.cafemetrix.cafelab.preparation.interfaces.rest.transform;

import com.cafemetrix.cafelab.preparation.domain.model.commands.UpdatePortfolioCommand;
import com.cafemetrix.cafelab.preparation.interfaces.rest.dto.UpdatePortfolioResource;

public class UpdatePortfolioCommandFromResourceAssembler {
    public static UpdatePortfolioCommand toCommandFromResource(
            Long userId, Long portfolioId, UpdatePortfolioResource resource) {
        return new UpdatePortfolioCommand(userId, portfolioId, resource.name());
    }
}
