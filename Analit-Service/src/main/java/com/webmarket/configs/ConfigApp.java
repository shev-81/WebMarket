package com.webmarket.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Конфиг сервиса.
 */
@Configuration
public class ConfigApp {

    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
