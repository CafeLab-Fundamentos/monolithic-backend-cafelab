package com.cafemetrix.cafelab.defects.domain.exceptions;

public class DefectNotFoundException extends RuntimeException {
    public DefectNotFoundException(Long defectId) {
        super("Defecto no encontrado");
    }
}
