package com.cafemetrix.cafelab.defects.interfaces.rest;

import com.cafemetrix.cafelab.defects.domain.exceptions.DefectNotFoundException;
import com.cafemetrix.cafelab.shared.interfaces.rest.resources.MessageResource;
import com.cafemetrix.cafelab.shared.interfaces.rest.support.CafeLabScopedExceptionHandlerSupport;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.cafemetrix.cafelab.defects.interfaces.rest")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class DefectsExceptionHandler extends CafeLabScopedExceptionHandlerSupport {

    @ExceptionHandler(DefectNotFoundException.class)
    public ResponseEntity<MessageResource> handleDefectNotFound(DefectNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResource(ex.getMessage()));
    }
}
