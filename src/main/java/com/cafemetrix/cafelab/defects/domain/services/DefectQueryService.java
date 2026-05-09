package com.cafemetrix.cafelab.defects.domain.services;

import com.cafemetrix.cafelab.defects.domain.model.aggregates.Defect;
import com.cafemetrix.cafelab.defects.domain.model.queries.GetDefectByIdForUserQuery;
import com.cafemetrix.cafelab.defects.domain.model.queries.GetDefectsByUserIdQuery;

import java.util.List;
import java.util.Optional;

public interface DefectQueryService {

    List<Defect> handle(GetDefectsByUserIdQuery query);

    Optional<Defect> handle(GetDefectByIdForUserQuery query);
}
