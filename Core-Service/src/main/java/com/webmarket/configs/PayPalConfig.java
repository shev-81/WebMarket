package com.webmarket.configs;

import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;

/**
 * PayPal Configuration.
 */
@Configuration
@PropertySource("secret.properties")
public class PayPalConfig {

    /**
     * The Client Id is issued in the PayPal personal account, and is
     * unique application number in the system.
     */
    @Value("${paypal.client-id}")
    private String clientId;

    /**
     * The PayPal secret key is issued in the PayPal personal account, and is
     * a unique number required to authorize the application in the system.
     */
    @Value("${paypal.client-secret}")
    private String secret;

    /**
     * PayPal environment, used as a DTO object for transmitting unique -
     * application numbers and secret key.
     */
    private PayPalEnvironment environment;

    @PostConstruct
    private void init() {
        this.environment = new PayPalEnvironment.Sandbox(clientId, secret);
    }

    /**
     * PayPal Customer.
     * @return
     */
    @Bean
    public PayPalHttpClient payPalClient() {
        return new PayPalHttpClient(environment);
    }
}
