package com.cafemetrix.cafelab.profiles.interfaces.rest.transform;

import com.cafemetrix.cafelab.profiles.domain.model.commands.UpdateProfileCommand;
import com.cafemetrix.cafelab.profiles.interfaces.rest.resources.UpdateProfileResource;

public class UpdateProfileCommandFromResourceAssembler {
    public static UpdateProfileCommand toCommandFromResource(Long userId, UpdateProfileResource resource) {
        return new UpdateProfileCommand(
                userId,
                resource.name(),
                resource.email(),
                resource.cafeteriaName(),
                resource.experience(),
                resource.paymentMethod(),
                resource.isFirstLogin(),
                resource.plan(),
                resource.hasPlan()
        );
    }
}
