package com.cafemetrix.cafelab.cuppingsessions.domain.exceptions;

public class CuppingSessionNotFoundException extends RuntimeException {
    public CuppingSessionNotFoundException(Long sessionId) {
        super("Sesión de cata no encontrada");
    }
}
