package com.webmarket.exceptions;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import webmarket.exceptions.AppError;
import webmarket.exceptions.BadConnectionServiceException;
import webmarket.exceptions.ResourceNotFoundException;

/**
 * Global exception interceptor. Wraps exceptions in ResponseEntity<> with an error code.
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Exception interceptor ResourceNotFoundException.
     * @param e ResourceNotFoundException
     * @return
     */
    @ExceptionHandler
    public ResponseEntity<AppError> catchResourceNotFoundException(ResourceNotFoundException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(), e.getMessage()), HttpStatus.NOT_FOUND);
    }

    /**
     * Bad Connection Service Exception Exception interceptor.
     * @param e BadConnectionServiceException
     * @return
     */
    @ExceptionHandler
    public ResponseEntity<AppError> catchBadConnectionServiceException(BadConnectionServiceException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new AppError(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}