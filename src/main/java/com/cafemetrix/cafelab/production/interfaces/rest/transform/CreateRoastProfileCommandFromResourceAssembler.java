package com.cafemetrix.cafelab.production.interfaces.rest.transform;

import com.cafemetrix.cafelab.production.domain.model.commands.CreateRoastProfileCommand;
import com.cafemetrix.cafelab.production.interfaces.rest.resources.CreateRoastProfileResource;

public class CreateRoastProfileCommandFromResourceAssembler {
    public static CreateRoastProfileCommand toCommandFromResource(
            Long ownerUserId,
            CreateRoastProfileResource resource) {
        Boolean fav =
                resource.isFavorite() != null ? resource.isFavorite() : Boolean.FALSE;
        return new CreateRoastProfileCommand(
                ownerUserId,
                resource.name(),
                resource.type(),
                resource.duration(),
                resource.tempStart(),
                resource.tempEnd(),
                resource.lot(),
                fav);
    }
}
