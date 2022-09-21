package com.webmarket.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import webmarket.exceptions.AppError;
import webmarket.exceptions.ResourceNotFoundException;

/**
 * Global exception interceptor. Wraps exceptions in ResponseEntity with an error code.
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Global exception interceptor. Wraps exceptions in ResponseEntity with an error code.
     * @param e
     * @return ResponseEntity
     */
    @ExceptionHandler
    public ResponseEntity<AppError> catchResourceNotFoundException(ResourceNotFoundException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(), e.getMessage()), HttpStatus.NOT_FOUND);
    }

    /**
     * ValidationException exception interceptor.
     * @param e
     * @return ResponseEntity
     */
    @ExceptionHandler
    public ResponseEntity<FieldsValidationError> catchValidationException(ValidationException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new FieldsValidationError(e.getErrorFieldsMessages()), HttpStatus.BAD_REQUEST);
    }

    /**
     * Interceptor of the Cardservice IntegrationException exception.
     * @param e
     * @return ResponseEntity
     */
    @ExceptionHandler
    public ResponseEntity<CartServiceIntegrationException> catchInternalServerError(CartServiceIntegrationException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new CartServiceIntegrationException("Сервис не работает"), HttpStatus.SERVICE_UNAVAILABLE);
    }
}
