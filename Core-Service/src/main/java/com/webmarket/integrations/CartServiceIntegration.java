package com.webmarket.integrations;

import com.webmarket.exceptions.CartServiceIntegrationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import webmarket.cart.CartDto;

/**
 * Integration with the shopping cart service.
 */
@Component
@RequiredArgsConstructor
public class CartServiceIntegration {

    /**
     * Client for requests to the shopping cart service.
     */
    private final WebClient cartServiceWebClient;

    /**
     * Sends a request to the shopping cart service to clear the user's trash.
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
     * Sends a request to the shopping cart service to receive the user's shopping cart.
     * @param username
     * @return CartDto
     */
    public CartDto getUserCart(String username) {
        CartDto cart = cartServiceWebClient.get()
                .uri("/api/v1/cart/0")
                .header("username", username)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new CartServiceIntegrationException("An incorrect request to the shopping cart service was made")))
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(new CartServiceIntegrationException("The shopping cart service is broken")))
                .bodyToMono(CartDto.class)
                .block();
        return cart;
    }
}
