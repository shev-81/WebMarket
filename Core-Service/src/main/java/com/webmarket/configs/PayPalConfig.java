package com.webmarket.configs;

import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;

/**
 * Конфигурация PayPal
 */
@Configuration
@PropertySource("secret.properties")
public class PayPalConfig {

    /**
     * Клиент Id выдается в личном кабинете PayPal, и представляет собой
     * уникальный номер приложения в системе.
     */
    @Value("${paypal.client-id}")
    private String clientId;

    /**
     * Секретный ключ PayPal выдается в личном кабинете PayPal, и представляет собой
     * уникальный номер необходимый для авторизации приложения в системе.
     */
    @Value("${paypal.client-secret}")
    private String secret;

    /**
     * Окружение PayPal, используется как объект DTO для передачи уникальных -
     * номера приложения и секретного ключа.
     */
    private PayPalEnvironment environment;

    @PostConstruct
    private void init() {
        this.environment = new PayPalEnvironment.Sandbox(clientId, secret);
    }

    /**
     * Клиент PayPal
     * @return
     */
    @Bean
    public PayPalHttpClient payPalClient() {
        return new PayPalHttpClient(environment);
    }
}
