package com.webmarket.listeners;

import com.webmarket.services.AnalitService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import webmarket.core.ProductDto;

/**
 * Kafka consumer that listens for products added to carts and forwards them
 * to the analytics service for popularity tracking.
 */
@Component
@RequiredArgsConstructor
public class AnalitKafkaListener {

    private final AnalitService analitService;

    /**
     * Consumes messages from the {@code ProductAnalit} topic.
     * Each message represents a product added to a customer's cart.
     *
     * @param product the product DTO received from Kafka
     */
    @KafkaListener(topics = "ProductAnalit", concurrency = "100")
    public void msgListener(ProductDto product) {
        analitService.register(product);
    }
}