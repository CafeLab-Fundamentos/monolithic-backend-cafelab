package com.cafemetrix.cafelab.calibrations.application.internal.queryservices;

import com.cafemetrix.cafelab.calibrations.domain.model.aggregates.GrindCalibration;
import com.cafemetrix.cafelab.calibrations.domain.model.queries.GetGrindCalibrationByIdForUserQuery;
import com.cafemetrix.cafelab.calibrations.domain.model.queries.GetGrindCalibrationsByUserIdQuery;
import com.cafemetrix.cafelab.calibrations.domain.services.GrindCalibrationQueryService;
import com.cafemetrix.cafelab.calibrations.infrastructure.persistence.jpa.repositories.GrindCalibrationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GrindCalibrationQueryServiceImpl implements GrindCalibrationQueryService {
    private final GrindCalibrationRepository repository;

    public GrindCalibrationQueryServiceImpl(GrindCalibrationRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<GrindCalibration> handle(GetGrindCalibrationsByUserIdQuery query) {
        return repository.findByUserIdOrderByCreatedAtDesc(query.userId());
    }

    @Override
    public Optional<GrindCalibration> handle(GetGrindCalibrationByIdForUserQuery query) {
        return repository.findByIdAndUserId(query.calibrationId(), query.userId());
    }
}
