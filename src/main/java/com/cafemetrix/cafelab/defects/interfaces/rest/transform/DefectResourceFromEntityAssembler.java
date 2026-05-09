package com.cafemetrix.cafelab.defects.interfaces.rest.transform;

import com.cafemetrix.cafelab.defects.domain.model.aggregates.Defect;
import com.cafemetrix.cafelab.defects.interfaces.rest.resources.DefectResource;

public class DefectResourceFromEntityAssembler {

    public static DefectResource toResourceFromEntity(Defect entity) {
        return new DefectResource(
                entity.getId(),
                entity.getUserId(),
                entity.getCoffeeDisplayName(),
                entity.getCoffeeRegion(),
                entity.getCoffeeVariety(),
                entity.getCoffeeTotalWeight(),
                entity.getName(),
                entity.getDefectType(),
                entity.getDefectWeight(),
                entity.getPercentage(),
                entity.getProbableCause(),
                entity.getSuggestedSolution()
        );
    }
}
