package com.cafemetrix.cafelab.production.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record Temperature(Double value) {
    public Temperature {
        if (value == null) {
            throw new IllegalArgumentException("La temperatura no puede ser nula");
        }
        if (value < 0 || value > 300) {
            throw new IllegalArgumentException("La temperatura debe estar entre 0 y 300°C");
        }
    }

    public Temperature() {
        this(null);
    }
}
