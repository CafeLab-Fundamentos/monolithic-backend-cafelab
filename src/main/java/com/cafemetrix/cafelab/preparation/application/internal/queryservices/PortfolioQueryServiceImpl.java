package com.cafemetrix.cafelab.preparation.application.internal.queryservices;

import com.cafemetrix.cafelab.preparation.domain.model.aggregates.Portfolio;
import com.cafemetrix.cafelab.preparation.domain.model.queries.GetPortfolioByIdForUserQuery;
import com.cafemetrix.cafelab.preparation.domain.model.queries.GetPortfoliosByUserIdQuery;
import com.cafemetrix.cafelab.preparation.domain.services.PortfolioQueryService;
import com.cafemetrix.cafelab.preparation.infrastructure.persistence.jpa.repositories.PortfolioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PortfolioQueryServiceImpl implements PortfolioQueryService {
    private final PortfolioRepository portfolioRepository;

    public PortfolioQueryServiceImpl(PortfolioRepository portfolioRepository) {
        this.portfolioRepository = portfolioRepository;
    }

    @Override
    public List<Portfolio> handle(GetPortfoliosByUserIdQuery query) {
        return portfolioRepository.findByUserIdOrderByCreatedAtDesc(query.userId());
    }

    @Override
    public Optional<Portfolio> handle(GetPortfolioByIdForUserQuery query) {
        return portfolioRepository.findByIdAndUserId(query.portfolioId(), query.userId());
    }
}
