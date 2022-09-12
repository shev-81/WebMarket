package com.webmarket.exceptions;

/**
 * Ошибка при интеграции с сервисом корзин.
 */
public class CartServiceIntegrationException extends RuntimeException {
    public CartServiceIntegrationException(String message) {
        super(message);
    }
}
