package org.example.jwt;

import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;

@Component
public final class JwtUtils {

    public static JwtAuthentication generate(Claims claims) {
        final JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setName(claims.get("name", String.class));
        return jwtInfoToken;
    }

}