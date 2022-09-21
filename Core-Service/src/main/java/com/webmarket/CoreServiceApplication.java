package com.webmarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

/**
 * Works with products and their categories, user orders and interactions
 * with the PayPal payment system. The service has configured integration with the user's shopping cart
 * (* (MS Card Service), as well as with PayPal. Implemented SOAP API for getting a list
 * products and a list of product categories. Communication between services via the Web Client.
 * Data about products and orders are stored in the Postgres database.
 */
@SpringBootApplication
@PropertySource("secret.properties")
public class CoreServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(CoreServiceApplication.class, args);
	}
}
