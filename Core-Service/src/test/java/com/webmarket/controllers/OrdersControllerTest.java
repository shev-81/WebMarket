package com.webmarket.controllers;

import com.webmarket.converters.OrderConverter;
import com.webmarket.converters.OrderItemsConverter;
import com.webmarket.entities.Order;
import com.webmarket.entities.OrderItem;
import com.webmarket.entities.Product;
import com.webmarket.services.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import webmarket.core.OrderDetailsDto;
import webmarket.core.OrderDto;
import webmarket.core.OrderItemDto;
import webmarket.exceptions.ResourceNotFoundException;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for OrdersController.
 */
@WebMvcTest(OrdersController.class)
class OrdersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @MockBean
    private OrderConverter orderConverter;

    // -----------------------------------------------------------------------
    // POST /api/v1/orders – create order
    // -----------------------------------------------------------------------

    @Test
    // Сценарий: POST /orders с username и телом — заказ создаётся, статус 201
    void createOrder_withValidData_shouldReturn201() throws Exception {
        doNothing().when(orderService).createOrder(eq("user123"), any(OrderDetailsDto.class));

        mockMvc.perform(post("/api/v1/orders")
                        .header("username", "user123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"postalcode\": \"101000\"," +
                                "\"adminAreaTwoTown\": \"Moscow\"," +
                                "\"addressLineOneStreet\": \"Tverskaya\"," +
                                "\"addressLineTwoApartmentNumber\": \"5\"," +
                                "\"email\": \"user@mail.ru\"," +
                                "\"phone\": \"+7-903-123-45-67\"" +
                                "}"))
                .andExpect(status().isCreated());

        verify(orderService, times(1)).createOrder(eq("user123"), any(OrderDetailsDto.class));
    }

    @Test
    // Сценарий: POST /orders без username — 400, сервис не вызывается
    void createOrder_withoutUsername_shouldFail() throws Exception {
        mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"postalcode\": \"101000\"," +
                                "\"adminAreaTwoTown\": \"Moscow\"," +
                                "\"addressLineOneStreet\": \"Tverskaya\"," +
                                "\"addressLineTwoApartmentNumber\": \"5\"," +
                                "\"email\": \"user@mail.ru\"," +
                                "\"phone\": \"+7-903-123-45-67\"" +
                                "}"))
                .andExpect(status().isBadRequest());

        verify(orderService, never()).createOrder(any(), any());
    }

    // -----------------------------------------------------------------------
    // GET /api/v1/orders – list orders for user
    // -----------------------------------------------------------------------

    @Test
    // Сценарий: GET /orders с username — возвращается список заказов пользователя
    void getCurrentUserOrders_withUsername_shouldReturnOrders() throws Exception {
        Order order = new Order();
        order.setId(1L);
        OrderDto orderDto = new OrderDto().setId(1L).setUsername("user123");

        when(orderService.findOrdersByUsername("user123")).thenReturn(Collections.singletonList(order));
        when(orderConverter.entityToDto(order)).thenReturn(orderDto);

        mockMvc.perform(get("/api/v1/orders")
                        .header("username", "user123")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].username").value("user123"));
    }

    @Test
    // Сценарий: GET /orders — у пользователя нет заказов, возвращается []
    void getCurrentUserOrders_whenEmpty_shouldReturnEmptyList() throws Exception {
        when(orderService.findOrdersByUsername("user123")).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/orders")
                        .header("username", "user123")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    // -----------------------------------------------------------------------
    // GET /api/v1/orders/{id}
    // -----------------------------------------------------------------------

    @Test
    // Сценарий: GET /orders/{id} — заказ найден, возвращается DTO
    void getOrderById_whenExists_shouldReturnOrder() throws Exception {
        Order order = new Order();
        order.setId(1L);
        OrderDto orderDto = new OrderDto().setId(1L).setUsername("user123");

        when(orderService.findById(1L)).thenReturn(Optional.of(order));
        when(orderConverter.entityToDto(order)).thenReturn(orderDto);

        mockMvc.perform(get("/api/v1/orders/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("user123"));
    }

    // Сценарий: GET /orders/{id} — заказ не найден, 404 "ORDER 404"
    @Test
    void getOrderById_whenNotFound_shouldReturn404() throws Exception {
        when(orderService.findById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/orders/{id}", 999L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.statusCode").value(404))
                .andExpect(jsonPath("$.message").value("ORDER 404"));
    }
}