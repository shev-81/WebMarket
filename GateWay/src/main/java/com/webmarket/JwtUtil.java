package com.webmarket;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Утилиты парсят токен на составляющие и дают ответ на 2 вопроса, Истек ли временной
 * буфер действия токена и действителен ли токен в данный момент.
 */
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Истек ли буфер времени действия ключа.
     * @param token
     * @return
     */
    private boolean isTokenExpired(String token) {
        return this.getAllClaimsFromToken(token).getExpiration().before(new Date());
    }

    /**
     * Действителен ли токен.
     * @param token
     * @return
     */
    public boolean isInvalid(String token) {
        return this.isTokenExpired(token);
    }
}
