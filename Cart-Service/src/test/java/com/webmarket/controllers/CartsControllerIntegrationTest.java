package com.webmarket.controllers;

import com.webmarket.model.Cart;
import com.webmarket.services.CartService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration test for CartsController.
 * <p>
 * Tests HTTP routing, parameter binding (path variables, request headers),
 * response serialization, and exception handling.
 * Infrastructure dependencies (Redis, Kafka, Core-Service via Feign) are mocked
 * at the CartService layer.
 */
@WebMvcTest(CartsController.class)
class CartsControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    // -----------------------------------------------------------------------
    // GET /api/v1/cart/{uuid} – getCart()
    // -----------------------------------------------------------------------

    @Test
    // Сценарий: неавторизованный пользователь запрашивает корзину по uuid — получает JSON с items и totalPrice
    void getCart_withoutUsername_shouldReturnCart() throws Exception {
        String uuid = "test-uuid";
        String expectedKey = "SPRING_WEB_APP_" + uuid;
        Cart cart = new Cart();
        cart.setTotalPrice(BigDecimal.ZERO);

        when(cartService.getCartUuidFromSuffix(uuid)).thenReturn(expectedKey);
        when(cartService.getCurrentCart(expectedKey)).thenReturn(cart);

        mockMvc.perform(get("/api/v1/cart/{uuid}", uuid)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items").isArray())
                .andExpect(jsonPath("$.totalPrice").isNumber());

        verify(cartService, times(1)).getCurrentCart(expectedKey);
    }

    @Test
    // Сценарий: авторизованный (header username) — ключ корзины строится от username, uuid игнорируется
    void getCart_withUsername_shouldUseUsernameAsKey() throws Exception {
        String uuid = "guest-uuid";
        String username = "user123";
        String expectedKey = "SPRING_WEB_APP_" + username;

        when(cartService.getCartUuidFromSuffix(username)).thenReturn(expectedKey);
        when(cartService.getCurrentCart(expectedKey)).thenReturn(new Cart());

        mockMvc.perform(get("/api/v1/cart/{uuid}", uuid)
                        .header("username", username)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(cartService, never()).getCartUuidFromSuffix(uuid);
        verify(cartService, times(1)).getCartUuidFromSuffix(username);
        verify(cartService, times(1)).getCurrentCart(expectedKey);
    }

    // -----------------------------------------------------------------------
    // GET /api/v1/cart/generate – getCart() (generate)
    // -----------------------------------------------------------------------

    @Test
    // Сценарий: запрос на генерацию нового uuid корзины — возвращает {"value":"..."}
    void generateCart_shouldReturnUuid() throws Exception {
        String generatedUuid = "generated-uuid-12345";

        when(cartService.generateCartUuid()).thenReturn(generatedUuid);

        mockMvc.perform(get("/api/v1/cart/generate")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").value(generatedUuid));

        verify(cartService, times(1)).generateCartUuid();
    }

    // -----------------------------------------------------------------------
    // GET /api/v1/cart/{uuid}/add/{productId} – add()
    // -----------------------------------------------------------------------

    @Test
    // Сценарий: гость добавляет товар в корзину — вызывается addToCart с uuid из path
    void addProduct_guestUser_shouldCallService() throws Exception {
        String uuid = "cart-uuid";
        Long productId = 42L;
        String expectedKey = "SPRING_WEB_APP_" + uuid;

        when(cartService.getCartUuidFromSuffix(uuid)).thenReturn(expectedKey);
        doNothing().when(cartService).addToCart(expectedKey, productId);

        mockMvc.perform(get("/api/v1/cart/{uuid}/add/{productId}", uuid, productId))
                .andExpect(status().isOk());

        verify(cartService, times(1)).addToCart(expectedKey, productId);
    }

    @Test
    // Сценарий: авторизованный добавляет товар — addToCart вызывается с ключом от username
    void addProduct_authorizedUser_shouldUseUsernameKey() throws Exception {
        String uuid = "guest-uuid";
        String username = "user123";
        Long productId = 42L;
        String expectedKey = "SPRING_WEB_APP_" + username;

        when(cartService.getCartUuidFromSuffix(username)).thenReturn(expectedKey);
        doNothing().when(cartService).addToCart(expectedKey, productId);

        mockMvc.perform(get("/api/v1/cart/{uuid}/add/{productId}", uuid, productId)
                        .header("username", username))
                .andExpect(status().isOk());

        verify(cartService, times(1)).addToCart(expectedKey, productId);
        verify(cartService, never()).getCartUuidFromSuffix(uuid);
    }

    // -----------------------------------------------------------------------
    // GET /api/v1/cart/{uuid}/decrement/{productId} – decrement()
    // -----------------------------------------------------------------------

    @Test
    // Сценарий: уменьшение количества товара — вызывается decrementItem
    void decrementProduct_shouldCallService() throws Exception {
        String uuid = "cart-uuid";
        Long productId = 10L;
        String expectedKey = "SPRING_WEB_APP_" + uuid;

        when(cartService.getCartUuidFromSuffix(uuid)).thenReturn(expectedKey);

        mockMvc.perform(get("/api/v1/cart/{uuid}/decrement/{productId}", uuid, productId))
                .andExpect(status().isOk());

        verify(cartService, times(1)).decrementItem(expectedKey, productId);
    }

    // -----------------------------------------------------------------------
    // GET /api/v1/cart/{uuid}/remove/{productId} – remove()
    // -----------------------------------------------------------------------

    @Test
    // Сценарий: удаление товара из корзины — вызывается removeItemFromCart
    void removeProduct_shouldCallService() throws Exception {
        String uuid = "cart-uuid";
        Long productId = 7L;
        String expectedKey = "SPRING_WEB_APP_" + uuid;

        when(cartService.getCartUuidFromSuffix(uuid)).thenReturn(expectedKey);

        mockMvc.perform(get("/api/v1/cart/{uuid}/remove/{productId}", uuid, productId))
                .andExpect(status().isOk());

        verify(cartService, times(1)).removeItemFromCart(expectedKey, productId);
    }

    // -----------------------------------------------------------------------
    // GET /api/v1/cart/{uuid}/clear – clear()
    // -----------------------------------------------------------------------

    @Test
    // Сценарий: очистка корзины — вызывается clearCart
    void clearCart_shouldCallService() throws Exception {
        String uuid = "cart-uuid";
        String expectedKey = "SPRING_WEB_APP_" + uuid;

        when(cartService.getCartUuidFromSuffix(uuid)).thenReturn(expectedKey);

        mockMvc.perform(get("/api/v1/cart/{uuid}/clear", uuid))
                .andExpect(status().isOk());

        verify(cartService, times(1)).clearCart(expectedKey);
    }

    // -----------------------------------------------------------------------
    // GET /api/v1/cart/{uuid}/merge – merge()
    // -----------------------------------------------------------------------

    @Test
    // Сценарий: слияние корзин авторизованного и гостя — merge() получает два разных ключа
    void mergeCart_withUsername_shouldMergeUserAndGuestCarts() throws Exception {
        String uuid = "guest-uuid";
        String username = "user123";
        String userCartKey = "SPRING_WEB_APP_" + username;
        String guestCartKey = "SPRING_WEB_APP_" + uuid;

        when(cartService.getCartUuidFromSuffix(username)).thenReturn(userCartKey);
        when(cartService.getCartUuidFromSuffix(uuid)).thenReturn(guestCartKey);

        mockMvc.perform(get("/api/v1/cart/{uuid}/merge", uuid)
                        .header("username", username))
                .andExpect(status().isOk());

        verify(cartService, times(1)).merge(userCartKey, guestCartKey);
    }

    @Test
    // Сценарий: merge без username — оба аргумента ключа ведутся от uuid
    void mergeCart_withoutUsername_shouldMergeEmptyWithGuest() throws Exception {
        String uuid = "guest-uuid";
        String guestCartKey = "SPRING_WEB_APP_" + uuid;

        when(cartService.getCartUuidFromSuffix(null)).thenReturn(guestCartKey);
        when(cartService.getCartUuidFromSuffix(uuid)).thenReturn(guestCartKey);

        mockMvc.perform(get("/api/v1/cart/{uuid}/merge", uuid))
                .andExpect(status().isOk());

        // Without a username header:
        //   getCurrentCartUuid(null, null) -> getCartUuidFromSuffix(null)
        //   getCurrentCartUuid(null, uuid) -> getCartUuidFromSuffix(uuid)
        verify(cartService, times(1)).getCartUuidFromSuffix(null);
        verify(cartService, times(1)).getCartUuidFromSuffix(uuid);
        verify(cartService, times(1)).merge(guestCartKey, guestCartKey);
    }
}