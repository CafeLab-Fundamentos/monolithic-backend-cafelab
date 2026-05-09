package com.cafemetrix.cafelab.coffees.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record CoffeeVariety(String value) {
    public CoffeeVariety() {
        this(null);
    }

    public CoffeeVariety {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("La variedad del café no puede ser nula o vacía");
        }
    }
}
