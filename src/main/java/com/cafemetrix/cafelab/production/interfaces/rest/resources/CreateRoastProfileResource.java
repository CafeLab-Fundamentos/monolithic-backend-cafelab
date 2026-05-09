package com.cafemetrix.cafelab.production.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/**
 * Cuerpo POST: sin userId (lo fija el JWT). {@code lot} es el id del lote de café.
 */
public record CreateRoastProfileResource(
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre no puede superar 50 caracteres")
    String name,

    @NotBlank(message = "El tipo es obligatorio")
    @Size(max = 20, message = "El tipo no puede superar 20 caracteres")
    String type,

    @NotNull(message = "La duración es obligatoria")
    @Positive(message = "La duración debe ser positiva")
    Integer duration,

    @NotNull(message = "La temperatura inicial es obligatoria")
    Double tempStart,

    @NotNull(message = "La temperatura final es obligatoria")
    Double tempEnd,

    @JsonProperty("lot")
    @NotNull(message = "Debe vincular un lote de café")
    @Positive(message = "Lote inválido")
    Long lot,

    Boolean isFavorite
) {}
