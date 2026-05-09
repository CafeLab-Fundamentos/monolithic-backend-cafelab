package com.cafemetrix.cafelab.defects.application.internal.queryservices;

import com.cafemetrix.cafelab.defects.domain.model.aggregates.Defect;
import com.cafemetrix.cafelab.defects.domain.model.queries.GetDefectByIdForUserQuery;
import com.cafemetrix.cafelab.defects.domain.model.queries.GetDefectsByUserIdQuery;
import com.cafemetrix.cafelab.defects.domain.services.DefectQueryService;
import com.cafemetrix.cafelab.defects.infrastructure.persistence.jpa.repositories.DefectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DefectQueryServiceImpl implements DefectQueryService {
    private final DefectRepository defectRepository;

    public DefectQueryServiceImpl(DefectRepository defectRepository) {
        this.defectRepository = defectRepository;
    }

    @Override
    public List<Defect> handle(GetDefectsByUserIdQuery query) {
        return defectRepository.findByUserIdOrderByCreatedAtDesc(query.userId());
    }

    @Override
    public Optional<Defect> handle(GetDefectByIdForUserQuery query) {
        return defectRepository.findByIdAndUserId(query.defectId(), query.userId());
    }
}
