package com.webmarket.converters;

import com.webmarket.entities.OrderItem;
import org.springframework.stereotype.Component;
import webmarket.core.OrderItemDto;

@Component
public class OrderItemsConverter {

    public OrderItem dtoToEntity(OrderItemDto orderItemDto) {
        throw new UnsupportedOperationException();
    }

    public OrderItemDto entityToDto(OrderItem orderItem) {
        return new OrderItemDto(orderItem.getProduct().getId(), orderItem.getProduct().getName(), orderItem.getQuantity(), orderItem.getPricePerProduct(), orderItem.getPrice());
    }
}
