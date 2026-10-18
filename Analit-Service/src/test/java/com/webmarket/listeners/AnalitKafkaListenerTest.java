package com.webmarket.listeners;

import com.webmarket.services.AnalitService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import webmarket.core.ProductDto;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

/**
 * Unit tests for AnalitKafkaListener.
 *
 * @see AnalitKafkaListener
 */
@ExtendWith(MockitoExtension.class)
class AnalitKafkaListenerTest {

    @Mock
    private AnalitService analitService;

    @InjectMocks
    private AnalitKafkaListener listener;

    @Test
    // Сценарий: получено одно сообщение из Kafka — сервис регистрирует продукт
    void msgListener_whenProductReceived_shouldRegisterProduct() {
        ProductDto product = new ProductDto(1L, "Apple", BigDecimal.valueOf(50));

        listener.msgListener(product);

        verify(analitService, times(1)).register(product);
    }

    @Test
    // Сценарий: получено несколько сообщений — сервис регистрирует каждый продукт
    void msgListener_whenMultipleProductsReceived_shouldRegisterEach() {
        ProductDto apple = new ProductDto(1L, "Apple", BigDecimal.valueOf(50));
        ProductDto banana = new ProductDto(2L, "Banana", BigDecimal.valueOf(30));

        listener.msgListener(apple);
        listener.msgListener(banana);

        verify(analitService, times(1)).register(apple);
        verify(analitService, times(1)).register(banana);
    }


}