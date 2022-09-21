package com.webmarket.controllers;

import com.webmarket.converters.OrderConverter;
import com.webmarket.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import webmarket.core.OrderDetailsDto;
import webmarket.core.OrderDto;
import webmarket.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for working with orders.
 */
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Tag(name = "Заказы", description = "Методы работы с заказами")
public class OrdersController {

    /**
     * Order service.
     */
    private final OrderService orderService;

    /**
     * Entity converter.
     */
    private final OrderConverter orderConverter;

    /**
     * Request to create a new order.
     * http://localhost:5555/core/api/v1/orders
     * @param username
     * @param orderDetailsDto
     */
    @PostMapping
    @Operation(
            summary = "Запрос на создание нового заказа",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content()
                    )
            }
    )
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@RequestHeader @Parameter(description = "Заголовок с именем пользователя", required = true) String username,
                            @RequestBody @Parameter(description = "DTO с деталями заказа", required = true) OrderDetailsDto orderDetailsDto) {
        orderService.createOrder(username, orderDetailsDto);
    }

    /**
     * Request for a list of orders.
     * http://localhost:5555/core/api/v1
     * @param username
     * @return
     */
    @GetMapping
    @Operation(
            summary = "Запрос на получение списка заказов",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = OrderDto.class))
                            )
                    )
            }
    )
    public List<OrderDto> getCurrentUserOrders(@RequestHeader @Parameter(description = "Заголовок с именем пользователя", required = true) String username) {
        return orderService.findOrdersByUsername(username).stream().map(o -> orderConverter.entityToDto(o)).collect(Collectors.toList());
    }

    /**
     * Request to receive an order by ID.
     * http://localhost:5555/core/api/v1/{id}
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Запрос на получение заказа по ID",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = OrderDto.class))
                    )
            }
    )
    public OrderDto getOrderById(@PathVariable Long id) {
        return orderConverter.entityToDto(orderService.findById(id).orElseThrow(() -> new ResourceNotFoundException("ORDER 404")));
    }
}



