package com.webmarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

/**
 * Работает с товарами и их категориями, заказами пользователей и взаимодействием
 * с платежной системой PayPal. В сервисе настроена интеграция с корзиной пользователя
 * (MS Cart Service), а так же с PayPal. Реализованн SOAP API для получения списка
 * товаров и списка категорий товаров. Общение межу сервисами через Web Client.
 * Данные о товарах и заказах хранятся в БД Postgres.
 */
@SpringBootApplication
@PropertySource("secret.properties")
public class CoreServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(CoreServiceApplication.class, args);
	}
}
