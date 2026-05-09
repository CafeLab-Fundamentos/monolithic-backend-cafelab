package com.cafemetrix.cafelab.preparation.infrastructure.persistence.jpa.repositories;

import com.cafemetrix.cafelab.preparation.domain.model.aggregates.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    List<Portfolio> findByUserIdOrderByCreatedAtDesc(Long userId);

    Optional<Portfolio> findByIdAndUserId(Long id, Long userId);
}
