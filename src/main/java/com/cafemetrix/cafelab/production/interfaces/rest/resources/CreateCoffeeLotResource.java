package com.cafemetrix.cafelab.production.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.List;

/**
 * Alta de lote; el {@code userId} lo fija el controlador a partir del JWT.
 */
public record CreateCoffeeLotResource(
    @JsonProperty("supplier_id")
    @NotNull(message = "Debe seleccionar un proveedor")
    @Positive(message = "Proveedor inválido")
    Long supplier_id,

    @JsonProperty("lot_name")
    @NotBlank(message = "El nombre del lote es obligatorio")
    @Size(max = 100, message = "El nombre del lote no puede superar 100 caracteres")
    String lot_name,

    @JsonProperty("coffee_type")
    @NotBlank(message = "El tipo de café es obligatorio")
    @Size(max = 50, message = "El tipo de café no puede superar 50 caracteres")
    String coffee_type,

    @JsonProperty("processing_method")
    @NotBlank(message = "El método de procesamiento es obligatorio")
    @Size(max = 50, message = "El método de procesamiento no puede superar 50 caracteres")
    String processing_method,

    @JsonProperty("altitude")
    @NotNull(message = "La altitud es obligatoria")
    @Positive(message = "La altitud debe ser un número positivo")
    Integer altitude,

    @JsonProperty("weight")
    @NotNull(message = "El peso es obligatorio")
    @Positive(message = "El peso debe ser un número positivo")
    Double weight,

    @JsonProperty("origin")
    @NotBlank(message = "El origen es obligatorio")
    @Size(max = 100, message = "El origen no puede superar 100 caracteres")
    String origin,

    @JsonProperty("status")
    @NotBlank(message = "El estado es obligatorio")
    @Size(max = 20, message = "El estado no puede superar 20 caracteres")
    String status,

    @JsonProperty("certifications")
    List<String> certifications
) {}
