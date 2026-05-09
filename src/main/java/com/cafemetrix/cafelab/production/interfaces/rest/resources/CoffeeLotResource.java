package com.cafemetrix.cafelab.production.interfaces.rest.resources;

import java.util.List;

public record CoffeeLotResource(
    Long id,
    Long userId,
    Long supplierId,
    String lotName,
    String coffeeType,
    String processingMethod,
    Integer altitude,
    Double weight,
    String origin,
    String status,
    List<String> certifications
) {}
