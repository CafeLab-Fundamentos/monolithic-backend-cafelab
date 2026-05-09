package com.cafemetrix.cafelab.preparation.interfaces.rest;

import com.cafemetrix.cafelab.preparation.domain.exceptions.IngredientNotFoundException;
import com.cafemetrix.cafelab.preparation.domain.exceptions.PortfolioNotFoundException;
import com.cafemetrix.cafelab.preparation.domain.exceptions.RecipeNotFoundException;
import com.cafemetrix.cafelab.shared.interfaces.rest.resources.MessageResource;
import com.cafemetrix.cafelab.shared.interfaces.rest.support.CafeLabScopedExceptionHandlerSupport;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.cafemetrix.cafelab.preparation.interfaces.rest")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class PreparationExceptionHandler extends CafeLabScopedExceptionHandlerSupport {

    @ExceptionHandler(RecipeNotFoundException.class)
    public ResponseEntity<MessageResource> handleRecipeNotFound(RecipeNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResource(ex.getMessage()));
    }

    @ExceptionHandler(PortfolioNotFoundException.class)
    public ResponseEntity<MessageResource> handlePortfolioNotFound(PortfolioNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResource(ex.getMessage()));
    }

    @ExceptionHandler(IngredientNotFoundException.class)
    public ResponseEntity<MessageResource> handleIngredientNotFound(IngredientNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResource(ex.getMessage()));
    }
}
