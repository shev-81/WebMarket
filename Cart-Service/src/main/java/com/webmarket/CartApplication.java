package com.webmarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.kafka.annotation.EnableKafka;

/**
 * Implements the functionality of the user's shopping cart, receives and stores a list of products in the object
 * recycle bin, then saves it to the Redis database. The basket algorithm implements recalculation
 * of added identical items of goods. When requested from MS Core Service, returns this
 * the object for the formation of the order.
 */
@EnableKafka
@SpringBootApplication
@EnableFeignClients
public class CartApplication {
	public static void main(String[] args) {
		SpringApplication.run(CartApplication.class, args);
	}
}
