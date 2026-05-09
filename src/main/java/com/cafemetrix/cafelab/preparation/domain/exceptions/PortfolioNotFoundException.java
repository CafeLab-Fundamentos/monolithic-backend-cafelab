package com.cafemetrix.cafelab.preparation.domain.exceptions;

public class PortfolioNotFoundException extends RuntimeException {
    public PortfolioNotFoundException(Long portfolioId) {
        super("Portafolio no encontrado");
    }
}
