package com.webmarket.converters;

import com.webmarket.entities.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import webmarket.core.OrderDto;

import java.util.stream.Collectors;

/**
 * Entity converter to DTO and back.
 */
@Component
@RequiredArgsConstructor
public class OrderConverter {

    private final OrderItemsConverter orderItemConverter;

    /**
     * Converts an order into a DTO order.
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
     * Temporarily not used and throws.
     * @see UnsupportedOperationException
     * @param orderDto
     * @return
     */
    public Order dtoToEntity(OrderDto orderDto) {
        throw new UnsupportedOperationException();
    }
}
