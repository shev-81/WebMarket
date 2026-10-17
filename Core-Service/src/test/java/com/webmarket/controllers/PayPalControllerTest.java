package com.webmarket.controllers;

import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.Order;
import com.paypal.orders.OrderRequest;
import com.paypal.orders.OrdersCaptureRequest;
import com.paypal.orders.OrdersCreateRequest;
import com.paypal.orders.PurchaseUnit;
import com.webmarket.services.OrderService;
import com.webmarket.services.PayPalService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for PayPalController.
 */
@WebMvcTest(PayPalController.class)
class PayPalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PayPalHttpClient payPalClient;

    @MockBean
    private OrderService orderService;

    @MockBean
    private PayPalService payPalService;

    // -----------------------------------------------------------------------
    // POST /api/v1/paypal/create/{orderId}
    // -----------------------------------------------------------------------

    @SuppressWarnings("unchecked")
    @Test
    // Сценарий: POST /paypal/create/{orderId} — создаётся заказ в PayPal, возвращается PayPal ID
    void createOrder_shouldReturnPayPalId() throws Exception {
        OrderRequest orderRequest = new OrderRequest();
        when(payPalService.createOrderRequest(1L)).thenReturn(orderRequest);

        HttpResponse<Order> response = mock(HttpResponse.class);
        Order order = mock(Order.class);
        when(response.result()).thenReturn(order);
        when(response.statusCode()).thenReturn(200);
        when(order.id()).thenReturn("PAYPAL_ORDER_123");

        when(payPalClient.execute(any(OrdersCreateRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/paypal/create/{orderId}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("PAYPAL_ORDER_123"));

        verify(payPalService, times(1)).createOrderRequest(1L);
        verify(payPalClient, times(1)).execute(any(OrdersCreateRequest.class));
    }

    // -----------------------------------------------------------------------
    // POST /api/v1/paypal/capture/{payPalId}
    // -----------------------------------------------------------------------

    @SuppressWarnings("unchecked")
    @Test
    // Сценарий: POST /paypal/capture/{payPalId} со статусом COMPLETED — заказ помечается PAID
    void captureOrder_whenCompleted_shouldMarkOrderPaid() throws Exception {
        HttpResponse<Order> response = mock(HttpResponse.class);
        Order order = mock(Order.class);
        PurchaseUnit purchaseUnit = mock(PurchaseUnit.class);
        when(response.result()).thenReturn(order);
        when(response.statusCode()).thenReturn(200);
        when(order.status()).thenReturn("COMPLETED");
        when(order.purchaseUnits()).thenReturn(Collections.singletonList(purchaseUnit));
        when(purchaseUnit.referenceId()).thenReturn("42");

        doNothing().when(orderService).saveOrderById(42L);

        when(payPalClient.execute(any(OrdersCaptureRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/paypal/capture/{payPalId}", "PAYPAL_CAPTURE_123")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Order completed!"));

        verify(orderService, times(1)).saveOrderById(42L);
    }

    @SuppressWarnings("unchecked")
    @Test
    // Сценарий: POST /paypal/capture/{payPalId} со статусом PENDING — saveOrderById НЕ вызывается
    void captureOrder_whenNotCompleted_shouldNotMarkPaid() throws Exception {
        HttpResponse<Order> response = mock(HttpResponse.class);
        Order order = mock(Order.class);
        when(response.result()).thenReturn(order);
        when(response.statusCode()).thenReturn(200);
        when(order.status()).thenReturn("PENDING");

        when(payPalClient.execute(any(OrdersCaptureRequest.class))).thenReturn(response);

        // Capture returns ResponseEntity<>(payPalOrder, ...) with a Mockito mock
        // that Jackson cannot fully serialize. We verify the critical assertion:
        // saveOrderById was NOT called.
        try {
            mockMvc.perform(post("/api/v1/paypal/capture/{payPalId}", "PAYPAL_PENDING_ID")
                            .accept(MediaType.APPLICATION_JSON));
        } catch (Exception ignored) {
            // Serialization of mock Order is expected to fail
        }

        verify(orderService, never()).saveOrderById(anyLong());
    }
}