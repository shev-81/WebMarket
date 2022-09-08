package com.webmarket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * ДТО с токеном для пользователя.
 */
@Data
@AllArgsConstructor
public class JwtResponse {

    /**
     * Токен пользователю.
     */
    private String token;
}
