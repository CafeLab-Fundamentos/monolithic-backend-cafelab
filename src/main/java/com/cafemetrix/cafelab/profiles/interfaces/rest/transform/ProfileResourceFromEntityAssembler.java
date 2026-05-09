package com.cafemetrix.cafelab.profiles.interfaces.rest.transform;

import com.cafemetrix.cafelab.profiles.domain.model.aggregates.Profile;
import com.cafemetrix.cafelab.profiles.interfaces.rest.resources.ProfileResource;

public class ProfileResourceFromEntityAssembler {
    /**
     * Convert Profile entity to ProfileResource
     * @param entity {@link Profile} entity to convert
     * @return {@link ProfileResource}.
     */
    public static ProfileResource toResourceFromEntity(Profile entity) {
        return new ProfileResource(
                entity.getId(),
                entity.getName(),
                entity.getEmailAddress(),
                entity.getRole(),
                entity.getCafeteriaName(),
                entity.getExperience(),
                entity.getProfilePicture(),
                entity.getPaymentMethod(),
                entity.getPlan(),
                entity.hasPlan()
        );
    }
}
