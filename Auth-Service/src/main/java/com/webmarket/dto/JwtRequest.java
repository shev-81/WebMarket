package com.webmarket.dto;

import lombok.Data;

/**
 * DTO with the username and password of the user.
 */
@Data
public class JwtRequest {

    /**
     * User login.
     */
    private String username;

    /**
     * The user's password.
     */
    private String password;
}
