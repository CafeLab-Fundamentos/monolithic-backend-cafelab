package com.cafemetrix.cafelab.coffees.domain.model.commands;

public record CreateCoffeeCommand(
        String name,
        String region,
        String variety,
        Double totalWeight
) {
}
