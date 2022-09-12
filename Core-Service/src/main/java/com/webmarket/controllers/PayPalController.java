package com.webmarket.controllers;

import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.Order;
import com.paypal.orders.OrderRequest;
import com.paypal.orders.OrdersCaptureRequest;
import com.paypal.orders.OrdersCreateRequest;
import com.webmarket.services.OrderService;
import com.webmarket.services.PayPalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

/**
 * Контроллер для работы с платежной системой PayPal
 */
@Controller
@RequestMapping("/api/v1/paypal")
@RequiredArgsConstructor
public class PayPalController {

    /**
     * Клиент для запросов.
     */
    private final PayPalHttpClient payPalClient;

    /**
     * Сервис заказов.
     */
    private final OrderService orderService;

    /**
     * Сервис PayPal.
     */
    private final PayPalService payPalService;

    /**
     * Запрос на создание заказа в платежной системе.
     * @param orderId
     * @return
     * @throws IOException
     */
    @PostMapping("/create/{orderId}")
    public ResponseEntity<?> createOrder(@PathVariable Long orderId) throws IOException {
        OrdersCreateRequest request = new OrdersCreateRequest();
        request.prefer("return=representation");
        request.requestBody(payPalService.createOrderRequest(orderId));
        HttpResponse<Order> response = payPalClient.execute(request);
        return new ResponseEntity<>(response.result().id(), HttpStatus.valueOf(response.statusCode()));
    }

    /**
     * Запрос при подтверждении заказа платежной системой.
     * @param payPalId
     * @return
     * @throws IOException
     */
    @PostMapping("/capture/{payPalId}")
    public ResponseEntity<?> captureOrder(@PathVariable String payPalId) throws IOException {
        OrdersCaptureRequest request = new OrdersCaptureRequest(payPalId);
        request.requestBody(new OrderRequest());

        HttpResponse<Order> response = payPalClient.execute(request);
        Order payPalOrder = response.result();
        if ("COMPLETED".equals(payPalOrder.status())) {
            long orderId = Long.parseLong(payPalOrder.purchaseUnits().get(0).referenceId());
            // меняем статус заказа в магазине на PAID - оплачен
            orderService.saveOrderById(orderId);
            return new ResponseEntity<>("Order completed!", HttpStatus.valueOf(response.statusCode()));
        }
        return new ResponseEntity<>(payPalOrder, HttpStatus.valueOf(response.statusCode()));
    }
}
