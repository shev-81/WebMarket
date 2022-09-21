package com.webmarket.controllers;

import com.webmarket.model.Cart;
import com.webmarket.services.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import webmarket.dto.StringResponse;

/**
 * The controller defines the methods of working with the bucket.
 */
@RestController
@Data
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
@Tag(name = "Корзина", description = "Методы работы с корзиной")
public class CartsController {

    /**
     * Shopping cart service.
     */
    private final CartService cartService;

    /**
     * Request to get an existing bucket.
     * http://localhost:5555/cart/api/v1/cart/{uuid}
     * @param username
     * @param uuid
     * @return Cart
     */
    @GetMapping("/{uuid}")
    @Operation(
            summary = "Запрос на получение существующей корзины",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Cart.class))
                    )
            }
    )
    public Cart getCart(@RequestHeader(required = false) String username, @PathVariable String uuid) {
        return cartService.getCurrentCart(getCurrentCartUuid(username, uuid));
    }

    /**
     * Request to create a bucket.
     * http://localhost:5555/cart/api/v1/cart/generate
     * @return
     */
    @GetMapping("/generate")
    @Operation(
            summary = "Запрос на создание корзины",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = StringResponse.class))
                    )
            }
    )
    public StringResponse getCart() {
        return new StringResponse(cartService.generateCartUuid());
    }

    /**
     * Request to add a product.
     * http://localhost:5555/cart/api/v1/cart/{uuid}/add/{productId}
     * @param username
     * @param uuid
     * @param productId
     */
    @GetMapping("/{uuid}/add/{productId}")
    @Operation(
            summary = "Запрос на добавление продукта",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema())
                    )
            }
    )
    public void add(@RequestHeader(required = false) String username, @PathVariable String uuid, @PathVariable Long productId) {
        cartService.addToCart(getCurrentCartUuid(username, uuid), productId);
    }

    /**
     * Request to reduce the number of products.
     * http://localhost:5555/cart/api/v1/cart/{uuid}/decrement/{productId}
     * @param username
     * @param uuid
     * @param productId
     */
    @GetMapping("/{uuid}/decrement/{productId}")
    @Operation(
            summary = "Запрос на уменьшение кол-ва продуктов",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema())
                    )
            }
    )
    public void decrement(@RequestHeader(required = false) String username, @PathVariable String uuid, @PathVariable Long productId) {
        cartService.decrementItem(getCurrentCartUuid(username, uuid), productId);
    }

    /**
     * Product Removal Request.
     * http://localhost:5555/cart/api/v1/cart/{uuid}/remove/{productId}
     * @param username
     * @param uuid
     * @param productId
     */
    @GetMapping("/{uuid}/remove/{productId}")
    @Operation(
            summary = "Запрос на удаление продукта",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema())
                    )
            }
    )
    public void remove(@RequestHeader(required = false) String username, @PathVariable String uuid, @PathVariable Long productId) {
        cartService.removeItemFromCart(getCurrentCartUuid(username, uuid), productId);
    }

    /**
     * Request to clear the content.
     * http://localhost:5555/cart/api/v1/cart/{uuid}/clear
     * @param username
     * @param uuid
     */
    @GetMapping("/{uuid}/clear")
    @Operation(
            summary = "Запрос на очистку содержимого",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema())
                    )
            }
    )
    public void clear(@RequestHeader(required = false) String username, @PathVariable String uuid) {
        cartService.clearCart(getCurrentCartUuid(username, uuid));
    }

    /**
     * Request to connect the baskets - baskets of an unauthorized user during authorization with an existing basket in the database.
     * http://localhost:5555/cart/api/v1/cart/{uuid}/merge
     * @param username
     * @param uuid
     */
    @GetMapping("/{uuid}/merge")
    @Operation(
            summary = "Запрос на соединение корзин - корзины неавторизованного пользователя при его авторизации с существующей корзиной в БД",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema())
                    )
            }
    )
    public void merge(@RequestHeader(required = false) String username, @PathVariable String uuid) {
        cartService.merge(
                getCurrentCartUuid(username, null),
                getCurrentCartUuid(null, uuid)
        );
    }

    /**
     * Returns the uuid of the bucket.
     * @param username
     * @param uuid
     * @return
     */
    private String getCurrentCartUuid(String username, String uuid) {
        if (username != null) {
            return cartService.getCartUuidFromSuffix(username);
        }
        return cartService.getCartUuidFromSuffix(uuid);
    }
}
