package com.cafemetrix.cafelab.calibrations.interfaces.rest.transform;

import com.cafemetrix.cafelab.calibrations.domain.model.commands.CreateGrindCalibrationCommand;
import com.cafemetrix.cafelab.calibrations.interfaces.rest.resources.CreateGrindCalibrationResource;

public class CreateGrindCalibrationCommandFromResourceAssembler {

    public static CreateGrindCalibrationCommand toCommand(
            Long userId, CreateGrindCalibrationResource resource) {
        return new CreateGrindCalibrationCommand(
                userId,
                resource.name(),
                resource.method(),
                resource.equipment(),
                resource.grindNumber(),
                resource.aperture(),
                resource.cupVolume(),
                resource.finalVolume(),
                resource.calibrationDate(),
                resource.comments(),
                resource.notes(),
                resource.sampleImage());
    }
}
