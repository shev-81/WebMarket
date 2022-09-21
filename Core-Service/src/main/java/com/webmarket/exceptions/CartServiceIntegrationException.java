package com.webmarket.exceptions;

/**
 * Error when integrating with the shopping cart service.
 */
public class CartServiceIntegrationException extends RuntimeException {
    public CartServiceIntegrationException(String message) {
        super(message);
    }
}
