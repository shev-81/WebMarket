package com.webmarket.services;

import com.webmarket.integrations.AnalitServiceIntegration;
import com.webmarket.integrations.ProductServiceIntegration;
import com.webmarket.model.Cart;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import webmarket.core.ProductDto;
import webmarket.exceptions.ResourceNotFoundException;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.UUID;
import java.util.function.Consumer;

@Service
@Data
@RequiredArgsConstructor
public class CartService {
    private final ProductServiceIntegration productsService;
    private final AnalitServiceIntegration analitService;
    private final RedisTemplate<String, Object> redisTemplate;
    private ArrayList<ProductDto>  listProductsForAnalit;

    @Value("${utils.cart.prefix}")
    private String cartPrefix;

    @PostConstruct
    private void init(){
        listProductsForAnalit = new ArrayList<>();
    }

    public String getCartUuidFromSuffix(String suffix) {
        return cartPrefix + suffix;
    }

    public String generateCartUuid() {
        return UUID.randomUUID().toString();
    }

    public Cart getCurrentCart(String cartKey) {
        if (!redisTemplate.hasKey(cartKey)) {
            redisTemplate.opsForValue().set(cartKey, new Cart());
        }
        return (Cart) redisTemplate.opsForValue().get(cartKey);
    }

    public void addToCart(String cartKey, Long productId) {
        ProductDto productDto = productsService.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Невозможно добавить продукт в корзину. Продукт не найдет, id: " + productId));
        listProductsForAnalit.add(productDto);
        execute(cartKey, c -> {
            c.add(productDto);
        });
    }

    public void clearCart(String cartKey) {
        execute(cartKey, Cart::clear);
    }

    public void removeItemFromCart(String cartKey, Long productId) {
        execute(cartKey, c -> c.remove(productId));
    }

    public void decrementItem(String cartKey, Long productId) {
        execute(cartKey, c -> c.decrement(productId));
    }

    public void merge(String userCartKey, String guestCartKey) {
        Cart guestCart = getCurrentCart(guestCartKey);
        Cart userCart = getCurrentCart(userCartKey);
        userCart.merge(guestCart);
        updateCart(guestCartKey, guestCart);
        updateCart(userCartKey, userCart);
    }

    private void execute(String cartKey, Consumer<Cart> action) {
        Cart cart = getCurrentCart(cartKey);
        action.accept(cart);
        redisTemplate.opsForValue().set(cartKey, cart);
    }

    public void updateCart(String cartKey, Cart cart) {
        redisTemplate.opsForValue().set(cartKey, cart);
    }

    public ArrayList<ProductDto> getListProductsForAnalit() {
        return listProductsForAnalit;
    }

    public void clearListProductsForAnalit(){
        listProductsForAnalit.clear();
    }
}