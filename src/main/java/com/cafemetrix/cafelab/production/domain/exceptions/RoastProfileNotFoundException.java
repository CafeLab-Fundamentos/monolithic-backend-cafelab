package com.cafemetrix.cafelab.production.domain.exceptions;

public class RoastProfileNotFoundException extends RuntimeException {
    public RoastProfileNotFoundException(Long roastProfileId) {
        super("Perfil de tueste no encontrado");
    }
}
