package com.cafemetrix.cafelab.calibrations.infrastructure.persistence.jpa.repositories;

import com.cafemetrix.cafelab.calibrations.domain.model.aggregates.GrindCalibration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GrindCalibrationRepository extends JpaRepository<GrindCalibration, Long> {

    List<GrindCalibration> findByUserIdOrderByCreatedAtDesc(Long userId);

    Optional<GrindCalibration> findByIdAndUserId(Long id, Long userId);
}
