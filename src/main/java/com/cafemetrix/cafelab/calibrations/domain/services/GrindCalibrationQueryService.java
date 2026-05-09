package com.cafemetrix.cafelab.calibrations.domain.services;

import com.cafemetrix.cafelab.calibrations.domain.model.aggregates.GrindCalibration;
import com.cafemetrix.cafelab.calibrations.domain.model.queries.GetGrindCalibrationByIdForUserQuery;
import com.cafemetrix.cafelab.calibrations.domain.model.queries.GetGrindCalibrationsByUserIdQuery;

import java.util.List;
import java.util.Optional;

public interface GrindCalibrationQueryService {

    List<GrindCalibration> handle(GetGrindCalibrationsByUserIdQuery query);

    Optional<GrindCalibration> handle(GetGrindCalibrationByIdForUserQuery query);
}
