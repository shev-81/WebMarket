package com.webmarket.exceptions;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;
import webmarket.exceptions.BadConnectionServiceException;

import java.net.ConnectException;

/**
 * Global exception interceptor. Wraps exceptions in ResponseEntity<> with an error code.
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Exception interceptor  HttpServerErrorException.Internal Server Error.
     * @param e
     * @return
     */
    @ExceptionHandler
    public ResponseEntity<BadConnectionServiceException> catchInternalServerError(HttpServerErrorException.InternalServerError e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new BadConnectionServiceException("Сервис не работает"), HttpStatus.SERVICE_UNAVAILABLE);
    }

    /**
     * Exception Interceptor ConnectException.
     * @param e
     * @return ResponseEntity
     */
    @ExceptionHandler
    public ResponseEntity<BadConnectionServiceException> catchConnectException(ConnectException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new BadConnectionServiceException("Сервис не работает"), HttpStatus.SERVICE_UNAVAILABLE);
    }
}
