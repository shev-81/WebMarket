package com.webmarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Содержит в себе Модуль безопасности выдающий токены прошедшим
 * авторизацию пользователям. Использует для своей работы Базу
 * данных Postgres, в которой хранит информацию о пользователях магазина.
 */
@SpringBootApplication
public class WebAuthServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebAuthServiceApplication.class, args);
    }
}