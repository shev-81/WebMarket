package com.webmarket.converters;

import com.webmarket.entities.OrderItem;
import org.springframework.stereotype.Component;
import webmarket.core.OrderItemDto;

/**
 * Конвертер сущности в ДТО и обратно.
 */
@Component
public class OrderItemsConverter {

    /**
     * Конвертирует ордерИтем ДТО в сущность ордерИтем.
     * @param orderItemDto
     * @return
     */
    public OrderItem dtoToEntity(OrderItemDto orderItemDto) {
        throw new UnsupportedOperationException();
    }

    /**
     * Конвертирует сущность ордерИтем в ордерИтем ДТО.
     * @param orderItem
     * @return
     */
    public OrderItemDto entityToDto(OrderItem orderItem) {
        return new OrderItemDto(orderItem.getProduct().getId(), orderItem.getProduct().getName(), orderItem.getQuantity(), orderItem.getPricePerProduct(), orderItem.getPrice());
    }
}
