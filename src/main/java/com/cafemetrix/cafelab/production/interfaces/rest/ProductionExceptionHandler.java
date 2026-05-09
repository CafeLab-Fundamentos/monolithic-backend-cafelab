package com.cafemetrix.cafelab.production.interfaces.rest;

import com.cafemetrix.cafelab.production.domain.exceptions.CoffeeLotNotFoundException;
import com.cafemetrix.cafelab.production.domain.exceptions.CoffeeLotOwnershipException;
import com.cafemetrix.cafelab.production.domain.exceptions.RoastProfileNotFoundException;
import com.cafemetrix.cafelab.production.domain.exceptions.SupplierNotFoundException;
import com.cafemetrix.cafelab.shared.interfaces.rest.resources.MessageResource;
import com.cafemetrix.cafelab.shared.interfaces.rest.support.CafeLabScopedExceptionHandlerSupport;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.cafemetrix.cafelab.production.interfaces.rest")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ProductionExceptionHandler extends CafeLabScopedExceptionHandlerSupport {

    @ExceptionHandler(CoffeeLotNotFoundException.class)
    public ResponseEntity<MessageResource> handleCoffeeLotNotFound(CoffeeLotNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResource(ex.getMessage()));
    }

    @ExceptionHandler(SupplierNotFoundException.class)
    public ResponseEntity<MessageResource> handleSupplierNotFound(SupplierNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResource(ex.getMessage()));
    }

    @ExceptionHandler(RoastProfileNotFoundException.class)
    public ResponseEntity<MessageResource> handleRoastProfileNotFound(RoastProfileNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResource(ex.getMessage()));
    }

    @ExceptionHandler(CoffeeLotOwnershipException.class)
    public ResponseEntity<MessageResource> handleCoffeeLotOwnership(CoffeeLotOwnershipException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResource(ex.getMessage()));
    }
}
