package com.cafemetrix.cafelab.preparation.application.internal.commandservices;

import com.cafemetrix.cafelab.preparation.domain.model.aggregates.Portfolio;
import com.cafemetrix.cafelab.preparation.domain.model.commands.CreatePortfolioCommand;
import com.cafemetrix.cafelab.preparation.domain.model.commands.DeletePortfolioCommand;
import com.cafemetrix.cafelab.preparation.domain.model.commands.UpdatePortfolioCommand;
import com.cafemetrix.cafelab.preparation.domain.services.PortfolioCommandService;
import com.cafemetrix.cafelab.preparation.infrastructure.persistence.jpa.repositories.PortfolioRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PortfolioCommandServiceImpl implements PortfolioCommandService {
    private final PortfolioRepository portfolioRepository;

    public PortfolioCommandServiceImpl(PortfolioRepository portfolioRepository) {
        this.portfolioRepository = portfolioRepository;
    }

    @Override
    public Long handle(CreatePortfolioCommand command) {
        var portfolio = new Portfolio(command);
        portfolio = portfolioRepository.save(portfolio);
        return portfolio.getId();
    }

    @Override
    public Optional<Portfolio> handle(UpdatePortfolioCommand command) {
        try {
            var existing = portfolioRepository.findByIdAndUserId(command.portfolioId(), command.userId());
            if (existing.isEmpty()) {
                return Optional.empty();
            }
            var portfolio = existing.get();
            portfolio.update(command);
            return Optional.of(portfolioRepository.save(portfolio));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean handle(DeletePortfolioCommand command) {
        try {
            var existing = portfolioRepository.findByIdAndUserId(command.portfolioId(), command.userId());
            if (existing.isEmpty()) {
                return false;
            }
            portfolioRepository.deleteById(command.portfolioId());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
