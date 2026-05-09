package com.cafemetrix.cafelab.coffees.interfaces.rest;

import com.cafemetrix.cafelab.coffees.domain.exceptions.CoffeeCreationFailedException;
import com.cafemetrix.cafelab.coffees.domain.exceptions.CoffeeNotFoundException;
import com.cafemetrix.cafelab.shared.interfaces.rest.resources.MessageResource;
import com.cafemetrix.cafelab.shared.interfaces.rest.support.CafeLabScopedExceptionHandlerSupport;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.cafemetrix.cafelab.coffees.interfaces.rest")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CoffeesExceptionHandler extends CafeLabScopedExceptionHandlerSupport {

    @ExceptionHandler(CoffeeNotFoundException.class)
    public ResponseEntity<MessageResource> handleCoffeeNotFound(CoffeeNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResource(ex.getMessage()));
    }

    @ExceptionHandler(CoffeeCreationFailedException.class)
    public ResponseEntity<MessageResource> handleCoffeeCreationFailed(CoffeeCreationFailedException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResource(ex.getMessage()));
    }
}
