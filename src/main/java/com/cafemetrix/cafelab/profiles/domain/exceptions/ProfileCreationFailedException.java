package com.cafemetrix.cafelab.profiles.domain.exceptions;

public class ProfileCreationFailedException extends RuntimeException {
    public ProfileCreationFailedException() {
        super("No se pudo crear el perfil");
    }
}
