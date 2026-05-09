package com.cafemetrix.cafelab.defects.infrastructure.persistence.jpa.repositories;

import com.cafemetrix.cafelab.defects.domain.model.aggregates.Defect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DefectRepository extends JpaRepository<Defect, Long> {

    List<Defect> findByUserIdOrderByCreatedAtDesc(Long userId);

    Optional<Defect> findByIdAndUserId(Long id, Long userId);
}
