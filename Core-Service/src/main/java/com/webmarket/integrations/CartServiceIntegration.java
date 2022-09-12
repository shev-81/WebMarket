package com.webmarket.integrations;

import com.webmarket.exceptions.CartServiceIntegrationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import webmarket.cart.CartDto;

/**
 * Интеграция с сервисом корзин.
 */
@Component
@RequiredArgsConstructor
public class CartServiceIntegration {

    /**
     * Клиент для запросов в сервис корзин.
     */
    private final WebClient cartServiceWebClient;

    /**
     * Посылает запрос в сервис корзин, для очистки корзины пользователя.
     * @param username
     */
    public void clearUserCart(String username) {
        cartServiceWebClient.get()
                .uri("/api/v1/cart/0/clear")
                .header("username", username)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    /**
     * Посылает запрос в сервис корзин для получения корзины пользователя.
     * @param username
     * @return
     */
    public CartDto getUserCart(String username) {
        CartDto cart = cartServiceWebClient.get()
                .uri("/api/v1/cart/0")
                .header("username", username)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new CartServiceIntegrationException("Выполнен некорректный запрос к сервису корзин")))
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(new CartServiceIntegrationException("Сервис корзин сломался")))
                .bodyToMono(CartDto.class)
                .block();
        return cart;
    }
}
