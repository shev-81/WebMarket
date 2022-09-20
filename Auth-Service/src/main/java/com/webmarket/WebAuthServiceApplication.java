package com.webmarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Contains a security module that issues tokens to users who have passed
 * authorization. Uses the Database for its work
 * Postgres data, which stores information about the store's users.
 */
@SpringBootApplication
public class WebAuthServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebAuthServiceApplication.class, args);
    }
}