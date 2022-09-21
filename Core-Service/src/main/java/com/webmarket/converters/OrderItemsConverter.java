package com.webmarket.converters;

import com.webmarket.entities.OrderItem;
import org.springframework.stereotype.Component;
import webmarket.core.OrderItemDto;

/**
 * Entity converter to DTO and back.
 */
@Component
public class OrderItemsConverter {

    /**
     * Converts an order And that is in essence an order item.
     * @param orderItemDto
     * @return
     */
    public OrderItem dtoToEntity(OrderItemDto orderItemDto) {
        throw new UnsupportedOperationException();
    }

    /**
     * Converts the entity OrderItem into an order item TO.
     * @param orderItem
     * @return
     */
    public OrderItemDto entityToDto(OrderItem orderItem) {
        return new OrderItemDto(orderItem.getProduct().getId(), orderItem.getProduct().getName(), orderItem.getQuantity(), orderItem.getPricePerProduct(), orderItem.getPrice());
    }
}
