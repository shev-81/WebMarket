package com.webmarket;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Utilities parse the token into components and give an answer to 2 questions, whether the temporary
 * buffer of the token has expired and whether the token is valid at the moment.
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
     * Whether the key expiration buffer has expired.
     * @param token
     * @return
     */
    private boolean isTokenExpired(String token) {
        return this.getAllClaimsFromToken(token).getExpiration().before(new Date());
    }

    /**
     * Whether the token is valid.
     * @param token
     * @return
     */
    public boolean isInvalid(String token) {
        return this.isTokenExpired(token);
    }
}
