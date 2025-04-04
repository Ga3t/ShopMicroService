package com.booking.auth_service.security;

import com.booking.core.enums.RoleEnum;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtGeneratorTest {

    private final SecretKey secretKey;
    private final Long expAccessToken = 86400000L;
    String secret = "fmlpa378u5413klnvds12390dcs!fskln?sad12#@esfsljjk*sdakjgd122esd";
    public JwtGeneratorTest() {
        System.out.println("Secret: " + secret);
        System.out.println("Exp Access: " + expAccessToken);
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }


    public String generateAccessToken(String username, Long userId, RoleEnum role) {
        Date currDate = new Date();
        Date expDate = new Date(currDate.getTime() + expAccessToken);

        return Jwts.builder()
                .setSubject(username)
                .claim("id", userId)
                .claim("role", role)
                .setIssuedAt(currDate)
                .setExpiration(expDate)
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }


    public boolean validateAccessToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public String getUsernameFromJwt(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }
}
