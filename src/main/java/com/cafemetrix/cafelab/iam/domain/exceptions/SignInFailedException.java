package com.cafemetrix.cafelab.iam.domain.exceptions;

public class SignInFailedException extends RuntimeException {
    public SignInFailedException() {
        super("Credenciales inválidas");
    }
}
