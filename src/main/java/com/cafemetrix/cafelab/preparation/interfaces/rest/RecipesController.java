package com.cafemetrix.cafelab.preparation.interfaces.rest;

import com.cafemetrix.cafelab.preparation.domain.exceptions.RecipeNotFoundException;
import com.cafemetrix.cafelab.preparation.interfaces.acl.PreparationContextFacade;
import com.cafemetrix.cafelab.preparation.interfaces.rest.dto.CreateRecipeResource;
import com.cafemetrix.cafelab.preparation.interfaces.rest.dto.RecipeResource;
import com.cafemetrix.cafelab.preparation.interfaces.rest.dto.UpdateRecipeResource;
import com.cafemetrix.cafelab.preparation.interfaces.rest.transform.CreateRecipeCommandFromResourceAssembler;
import com.cafemetrix.cafelab.preparation.interfaces.rest.transform.RecipeResourceFromEntityAssembler;
import com.cafemetrix.cafelab.preparation.interfaces.rest.transform.UpdateRecipeCommandFromResourceAssembler;
import com.cafemetrix.cafelab.iam.infrastructure.authorization.sfs.support.CurrentProfileIdResolver;
import com.cafemetrix.cafelab.shared.interfaces.rest.resources.MessageResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/recipes", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Recipes", description = "Recetas de preparación por perfil (barista)")
public class RecipesController {
    private final PreparationContextFacade preparationContextFacade;
    private final CurrentProfileIdResolver currentProfileIdResolver;

    public RecipesController(
            PreparationContextFacade preparationContextFacade,
            CurrentProfileIdResolver currentProfileIdResolver) {
        this.preparationContextFacade = preparationContextFacade;
        this.currentProfileIdResolver = currentProfileIdResolver;
    }

    private Optional<Long> resolveCurrentUserId() {
        return currentProfileIdResolver.resolveProfileId();
    }

    private ResponseEntity<?> unauthorized(String message) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResource(message));
    }

    @Operation(summary = "Crear receta (perfil desde JWT)")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createRecipe(@RequestBody CreateRecipeResource resource) {
        Optional<Long> userIdOpt = resolveCurrentUserId();
        if (userIdOpt.isEmpty()) {
            return unauthorized("Usuario no autenticado o perfil no encontrado");
        }
        try {
            var command = CreateRecipeCommandFromResourceAssembler.toCommand(userIdOpt.get(), resource);
            var created = preparationContextFacade.createRecipe(command);
            if (created.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(new MessageResource("No se pudo crear la receta. Intente de nuevo."));
            }
            var ingredients = preparationContextFacade.getIngredientsByRecipeId(created.get().getId());
            var recipeResource =
                    RecipeResourceFromEntityAssembler.toResourceFromEntity(created.get(), ingredients);
            return new ResponseEntity<>(recipeResource, HttpStatus.CREATED);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(new MessageResource(ex.getMessage()));
        }
    }

    @Operation(summary = "Listar recetas del perfil autenticado")
    @GetMapping
    public ResponseEntity<?> getRecipesForCurrentProfile() {
        Optional<Long> userIdOpt = resolveCurrentUserId();
        if (userIdOpt.isEmpty()) {
            return unauthorized("Usuario no autenticado o perfil no encontrado");
        }
        var recipes = preparationContextFacade.getRecipesByUserId(userIdOpt.get());
        var recipeResources = recipes.stream()
                .map(recipe -> {
                    var ingredients =
                            preparationContextFacade.getIngredientsByRecipeId(recipe.getId());
                    return RecipeResourceFromEntityAssembler.toResourceFromEntity(recipe, ingredients);
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(recipeResources);
    }

    @Operation(summary = "Obtener receta por id (solo si pertenece al perfil)")
    @GetMapping("/{recipeId}")
    public ResponseEntity<?> getRecipeById(@PathVariable Long recipeId) {
        Optional<Long> userIdOpt = resolveCurrentUserId();
        if (userIdOpt.isEmpty()) {
            return unauthorized("Usuario no autenticado o perfil no encontrado");
        }
        var recipe = preparationContextFacade.getRecipeByIdForUser(recipeId, userIdOpt.get());
        if (recipe.isEmpty()) {
            throw new RecipeNotFoundException(recipeId);
        }
        var ingredients = preparationContextFacade.getIngredientsByRecipeId(recipeId);
        var recipeResource = RecipeResourceFromEntityAssembler.toResourceFromEntity(recipe.get(), ingredients);
        return ResponseEntity.ok(recipeResource);
    }

    @Operation(summary = "Actualizar receta")
    @PutMapping(value = "/{recipeId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateRecipe(
            @PathVariable Long recipeId, @RequestBody UpdateRecipeResource resource) {
        Optional<Long> userIdOpt = resolveCurrentUserId();
        if (userIdOpt.isEmpty()) {
            return unauthorized("Usuario no autenticado o perfil no encontrado");
        }
        try {
            var updateCommand =
                    UpdateRecipeCommandFromResourceAssembler.toCommandFromResource(
                            userIdOpt.get(), recipeId, resource);
            var updated = preparationContextFacade.updateRecipe(updateCommand);
            if (updated.isEmpty()) {
                throw new RecipeNotFoundException(recipeId);
            }
            var ingredients = preparationContextFacade.getIngredientsByRecipeId(recipeId);
            var recipeResource =
                    RecipeResourceFromEntityAssembler.toResourceFromEntity(updated.get(), ingredients);
            return ResponseEntity.ok(recipeResource);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(new MessageResource(ex.getMessage()));
        }
    }

    @Operation(summary = "Eliminar receta")
    @DeleteMapping("/{recipeId}")
    public ResponseEntity<?> deleteRecipe(@PathVariable Long recipeId) {
        Optional<Long> userIdOpt = resolveCurrentUserId();
        if (userIdOpt.isEmpty()) {
            return unauthorized("Usuario no autenticado o perfil no encontrado");
        }
        boolean deleted = preparationContextFacade.deleteRecipe(recipeId, userIdOpt.get());
        if (deleted) {
            return ResponseEntity.ok(new MessageResource("Receta eliminada exitosamente"));
        }
        throw new RecipeNotFoundException(recipeId);
    }
}
