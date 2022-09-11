package com.webmarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Получает через Kafka данные у MS Core Service, и сохраняет их в БД MySQL. Данные
 * содержат в себе названия продуктов, наиболее часто добавляемых в корзины
 * покупателей. При загрузке стартовой страницы магазина, данные показываются в виде
 * списка востребованных товаров.
 */
@EnableKafka
@EnableScheduling
@ConditionalOnProperty(name = "scheduler.enabled", matchIfMissing = true)
@SpringBootApplication
public class AnalitApplication {
    public static void main(String[] args) {
        SpringApplication.run(AnalitApplication.class, args);
    }
}
