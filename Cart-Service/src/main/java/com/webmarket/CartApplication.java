package com.webmarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Реализует функционал корзины пользователя, получает и сохраняет список товаров в объект
 * корзины, затем сохраняет его в БД Redis. В алгоритме корзины реализован перерасчет
 * добавляемых одинаковых позиций товаров. При запросе из MS Core Service, отдает этот
 * объект для формирования заказа.
 */
@SpringBootApplication
public class CartApplication {
	public static void main(String[] args) {
		SpringApplication.run(CartApplication.class, args);
	}
}
