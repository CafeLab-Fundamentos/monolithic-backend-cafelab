package com.cafemetrix.cafelab.production.interfaces.rest.resources;

public record RoastProfileResource(
    Long id,
    Long userId,
    String name,
    String type,
    Integer duration,
    Double tempStart,
    Double tempEnd,
    Long coffeeLotId,
    Boolean isFavorite
) {}
