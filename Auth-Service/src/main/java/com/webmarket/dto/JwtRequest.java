package com.webmarket.dto;

import lombok.Data;

/**
 * ДТО с логином и паролем пользователя.
 */
@Data
public class JwtRequest {

    /**
     * Логин пользователя.
     */
    private String username;

    /**
     * Пароль пользователя.
     */
    private String password;
}
