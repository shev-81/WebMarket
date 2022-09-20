package com.webmarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Receives data from MS Core Service via Kafka, and stores it in the MySQL database. Data
 * contain the names of the products most frequently added to the baskets
 * buyers. When loading the store's home page, the data is shown as
 * a list of products in demand.
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
