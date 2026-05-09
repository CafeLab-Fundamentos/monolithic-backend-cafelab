package com.cafemetrix.cafelab.preparation.infrastructure.persistence.jpa.repositories;

import com.cafemetrix.cafelab.preparation.domain.model.aggregates.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findByUserIdOrderByCreatedAtDesc(Long userId);

    Optional<Recipe> findByIdAndUserId(Long id, Long userId);

    List<Recipe> findByPortfolioId(Long portfolioId);
}
