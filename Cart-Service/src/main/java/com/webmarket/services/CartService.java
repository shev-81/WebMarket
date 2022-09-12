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
 * Сервис для работы с корзиной, основные задачи это выдача объекта корзины пользователю.
 * Если незарегистрированный то выдается
 */
@Service
@Data
@RequiredArgsConstructor
public class CartService {

    /**
     * Прокси для обращения в продуктовый сервис
     */
    private final ProductServiceIntegration productsService;

    /**
     * Прокси для обращения в сервис Аналитики.
     */
    private final AnalitServiceIntegration analitService;

    /**
     * Шаблон для работы с Redis.
     */
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * Префикс для каждой корзины
     */
    @Value("${utils.cart.prefix}")
    private String cartPrefix;

    /**
     * Шаблон для отправки сообщений в шину обмена данными кафки.
     */
    @Qualifier(value = "KafkaAnalit")
    @Autowired
    private KafkaTemplate<Long, ProductDto> kafkaTemplate;

    /**
     * Выдает уникальный номер корзины с суфиксом.
     * @param suffix
     * @return String
     */
    public String getCartUuidFromSuffix(String suffix) {
        return cartPrefix + suffix;
    }

    /**
     * Возвращает рандомно сгенерированную строку, используемую как
     * уникальный индификатор корзины.
     * @return String
     */
    public String generateCartUuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * По уникальному ключу корзины достает из Redis объект корзины.
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
     * Добавление продукта в корзину и отправка сообщения через
     * кафку в сервис аналитики о добавлении продукта в корзину.
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
}