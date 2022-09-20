package com.webmarket.services;

import com.webmarket.integrations.AnalitServiceIntegration;
import com.webmarket.integrations.ProductServiceIntegration;
import com.webmarket.model.Cart;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import webmarket.core.ProductDto;
import webmarket.exceptions.ResourceNotFoundException;

import java.util.UUID;
import java.util.function.Consumer;

/**
 * A service for working with a basket, the main tasks are to issue a basket object to the user.
 * If unregistered, it is issued.
 */
@Service
@Data
@RequiredArgsConstructor
public class CartService {

    /**
     * Proxy for contacting the grocery service.
     */
    private final ProductServiceIntegration productsService;

    /**
     * Proxy for accessing the Analytics service.
     */
    private final AnalitServiceIntegration analitService;

    /**
     * Template for working with Redis.
     */
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * Prefix for each basket.
     */
    @Value("${utils.cart.prefix}")
    private String cartPrefix;

    /**
     * A template for sending messages to the kafka data exchange bus.
     */
    @Qualifier(value = "KafkaAnalit")
    @Autowired
    private KafkaTemplate<Long, ProductDto> kafkaTemplate;

    /**
     * Issues a unique bucket number with a suffix.
     * @param suffix
     * @return String
     */
    public String getCartUuidFromSuffix(String suffix) {
        return cartPrefix + suffix;
    }

    /**
     * Returns a randomly generated string used as
     * unique basket identifier.
     * @return String
     */
    public String generateCartUuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * By the unique key of the basket, it takes out the basket object from Redis.
     * @param cartKey
     * @return
     */
    public Cart getCurrentCart(String cartKey) {
        if (!redisTemplate.hasKey(cartKey)) {
            redisTemplate.opsForValue().set(cartKey, new Cart());
        }
        return (Cart) redisTemplate.opsForValue().get(cartKey);
    }

    /**
     * Adding a product to the cart and sending a message via
     * kafka to the analytics service about adding a product to the cart.
     * @param cartKey
     * @param productId
     */
    public void addToCart(String cartKey, Long productId) {
        ProductDto productDto = productsService.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Невозможно добавить продукт в корзину. Продукт не найдет, id: " + productId));
        execute(cartKey, c -> {
            c.add(productDto);
        });
        ListenableFuture<SendResult<Long, ProductDto>> future = kafkaTemplate.send("ProductAnalit", productDto);
        //future.addCallback(System.out::println, System.err::println);
        kafkaTemplate.flush();
    }

    /**
     * Clears the trash.
     * @param cartKey
     */
    public void clearCart(String cartKey) {
        execute(cartKey, Cart::clear);
    }

    /**
     * Removes an item from the trash.
     * @param cartKey
     * @param productId
     */
    public void removeItemFromCart(String cartKey, Long productId) {
        execute(cartKey, c -> c.remove(productId));
    }

    /**
     * Reduces the amount of product.
     * @param cartKey
     * @param productId
     */
    public void decrementItem(String cartKey, Long productId) {
        execute(cartKey, c -> c.decrement(productId));
    }

    /**
     * Combines the guest basket with the basket of the registered user.
     * @param userCartKey
     * @param guestCartKey
     */
    public void merge(String userCartKey, String guestCartKey) {
        Cart guestCart = getCurrentCart(guestCartKey);
        Cart userCart = getCurrentCart(userCartKey);
        userCart.merge(guestCart);
        updateCart(guestCartKey, guestCart);
        updateCart(userCartKey, userCart);
    }

    /**
     * Executes a command for the bucket.
     * @param cartKey
     * @param action
     */
    private void execute(String cartKey, Consumer<Cart> action) {
        Cart cart = getCurrentCart(cartKey);
        action.accept(cart);
        redisTemplate.opsForValue().set(cartKey, cart);
    }

    /**
     * Updates the bucket object in the Redis database.
     * @param cartKey
     * @param cart
     */
    public void updateCart(String cartKey, Cart cart) {
        redisTemplate.opsForValue().set(cartKey, cart);
    }
}