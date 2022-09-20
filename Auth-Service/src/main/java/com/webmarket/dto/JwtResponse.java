package com.webmarket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO with a token for the user.
 */
@Data
@AllArgsConstructor
public class JwtResponse {

    /**
     * Token to the user.
     */
    private String token;
}
