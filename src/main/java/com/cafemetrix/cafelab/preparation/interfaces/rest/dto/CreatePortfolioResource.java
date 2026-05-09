package com.cafemetrix.cafelab.preparation.interfaces.rest.dto;

public record CreatePortfolioResource(String name) {
    public CreatePortfolioResource {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name es requerido");
        if (name.length() > 100) throw new IllegalArgumentException("Name no puede exceder 100 caracteres");
    }
}
