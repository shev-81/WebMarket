package com.webmarket.services;


import com.paypal.orders.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webmarket.exceptions.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Основная задача сервиса сформировать запрос на основании данных из заказа пользователя.
 */
@Service
@RequiredArgsConstructor
public class PayPalService {

    /**
     * Сервис заказов.
     */
    private final OrderService orderService;

    /**
     * Создает запрос в систему PayPal на основании данных полученных из заказа пользователя.
     * @param orderId
     * @return
     */
    @Transactional
    public OrderRequest createOrderRequest(Long orderId) {
        com.webmarket.entities.Order order = orderService.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Заказ не найден"));

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.checkoutPaymentIntent("CAPTURE");

        ApplicationContext applicationContext = new ApplicationContext()
                .brandName("test-market")
                .landingPage("BILLING")
                .shippingPreference("SET_PROVIDED_ADDRESS");
        orderRequest.applicationContext(applicationContext);

        List<PurchaseUnitRequest> purchaseUnitRequests = new ArrayList<>();
        PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest()
                .referenceId(orderId.toString())
                .description("Market Order" + order.getId())
                .amountWithBreakdown(new AmountWithBreakdown().currencyCode("RUB").value(String.valueOf(order.getTotalPrice()))
                        .amountBreakdown(new AmountBreakdown().itemTotal(new Money().currencyCode("RUB").value(String.valueOf(order.getTotalPrice())))))
                .items(order.getItems().stream()
                        .map(orderItem -> new Item()
                                .name(orderItem.getProduct().getName())
                                .unitAmount(new Money().currencyCode("RUB").value(String.valueOf(orderItem.getPrice())))
                                .quantity(String.valueOf(orderItem.getQuantity())))
                        .collect(Collectors.toList()))
                .shippingDetail(new ShippingDetail().name(new Name().fullName(order.getFio()))
                        .addressPortable(new AddressPortable().addressLine1(order.getAddressLineOneStreet()).addressLine2(order.getAddressLineTwoApartmentNumber())
                                .adminArea2(order.getAdminAreaTwoTown()).postalCode(order.getPostalCode()).countryCode("RU")));
        purchaseUnitRequests.add(purchaseUnitRequest);
        orderRequest.purchaseUnits(purchaseUnitRequests);
        return orderRequest;
    }
}
