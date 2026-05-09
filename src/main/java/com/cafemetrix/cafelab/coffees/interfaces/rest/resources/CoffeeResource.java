package com.cafemetrix.cafelab.coffees.interfaces.rest.resources;

public record CoffeeResource(
        Long id,
        String name,
        String region,
        String variety,
        Double totalWeight
) {
}
