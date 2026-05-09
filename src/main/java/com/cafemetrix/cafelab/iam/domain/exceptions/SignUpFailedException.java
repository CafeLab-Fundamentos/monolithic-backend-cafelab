package com.cafemetrix.cafelab.iam.domain.exceptions;

public class SignUpFailedException extends RuntimeException {
    public SignUpFailedException() {
        super("No se pudo registrar el usuario");
    }
}
