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
 * Контроллер для работы с заказами.
 */
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Tag(name = "Заказы", description = "Методы работы с заказами")
public class OrdersController {

    /**
     * Сервис заказов.
     */
    private final OrderService orderService;

    /**
     * Конвертер сущностей.
     */
    private final OrderConverter orderConverter;

    /**
     * Запрос на создание нового заказа.
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
     * Запрос на получение списка заказов.
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
     * Запрос на получение заказа по ID.
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



