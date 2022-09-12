package com.webmarket.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import webmarket.exceptions.AppError;
import webmarket.exceptions.ResourceNotFoundException;

/**
 * Глобальный перехватчик исключений. Оборачивает исключения в ResponseEntity<> с кодом ошибки.
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Перехватчик исключения ResourceNotFoundException.
     * @param e
     * @return ResponseEntity
     */
    @ExceptionHandler
    public ResponseEntity<AppError> catchResourceNotFoundException(ResourceNotFoundException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(), e.getMessage()), HttpStatus.NOT_FOUND);
    }

    /**
     * Перехватчик исключения ValidationException.
     * @param e
     * @return ResponseEntity
     */
    @ExceptionHandler
    public ResponseEntity<FieldsValidationError> catchValidationException(ValidationException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new FieldsValidationError(e.getErrorFieldsMessages()), HttpStatus.BAD_REQUEST);
    }

    /**
     * Перехватчик исключения CartServiceIntegrationException.
     * @param e
     * @return ResponseEntity
     */
    @ExceptionHandler
    public ResponseEntity<CartServiceIntegrationException> catchInternalServerError(CartServiceIntegrationException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new CartServiceIntegrationException("Сервис не работает"), HttpStatus.SERVICE_UNAVAILABLE);
    }
}
