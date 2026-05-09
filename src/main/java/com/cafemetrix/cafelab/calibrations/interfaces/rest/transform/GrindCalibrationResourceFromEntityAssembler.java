package com.cafemetrix.cafelab.calibrations.interfaces.rest.transform;

import com.cafemetrix.cafelab.calibrations.domain.model.aggregates.GrindCalibration;
import com.cafemetrix.cafelab.calibrations.interfaces.rest.resources.GrindCalibrationResource;

public class GrindCalibrationResourceFromEntityAssembler {

    public static GrindCalibrationResource toResourceFromEntity(GrindCalibration e) {
        return new GrindCalibrationResource(
                e.getId(),
                e.getUserId(),
                e.getName(),
                e.getMethod(),
                e.getEquipment(),
                e.getGrindNumber(),
                e.getAperture(),
                e.getCupVolume(),
                e.getFinalVolume(),
                e.getCalibrationDate(),
                e.getComments(),
                e.getNotes(),
                e.getSampleImage());
    }
}
