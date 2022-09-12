package com.webmarket.converters;

import com.webmarket.entities.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import webmarket.core.OrderDto;

import java.util.stream.Collectors;

/**
 * Конвертер сущности в ДТО и обратно.
 */
@Component
@RequiredArgsConstructor
public class OrderConverter {

    private final OrderItemsConverter orderItemConverter;

    /**
     * Конвертирует заказ в ДТО заказ.
     * @param order
     * @return
     */
    public OrderDto entityToDto(Order order) {
        OrderDto out = new OrderDto();
        out.setId(order.getId())
                .setUsername(order.getUsername())
                .setTotalPrice(order.getTotalPrice())
                .setPostalCode(order.getPostalCode())
                .setAdminAreaTwoTown(order.getAdminAreaTwoTown())
                .setAddressLineOneStreet(order.getAddressLineOneStreet())
                .setAddressLineTwoApartmentNumber(order.getAddressLineTwoApartmentNumber())
                .setFio(order.getFio())
                .setPhone(order.getPhone())
                .setEmail(order.getEmail())
                .setStatus(order.getStatus())
                .setItems(order.getItems()
                        .stream()
                        .map(orderItemConverter::entityToDto)
                        .collect(Collectors.toList()));
        return out;
    }

    /**
     * Временно не используется и кидает.
     * @see UnsupportedOperationException
     * @param orderDto
     * @return
     */
    public Order dtoToEntity(OrderDto orderDto) {
        throw new UnsupportedOperationException();
    }
}
