package org.handnotes.auth;

import com.mongodb.internal.connection.Time;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.handnotes.model.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.PublicKey;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class JwtService {

    private static final String SECRET_KEY = "nEwLhYS0ApTbKwkkCOQAhNMkt46eH3Hm";
    private final long accessTokenValidity = 60*60*1000;

    private static final String TOKEN_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    public String extractUsername(String token) throws Exception {
        return extractClaims(token).get("username", String.class);
    }

    public boolean isExpired(String token) {
        Claims claims = extractClaims(token);
        Date expirationDate = claims.getExpiration();
        Date current = new Date();
        return expirationDate != null && expirationDate.before(current);
    }

    public boolean validateToken(String token){
        return isExpired(token);
    }

    private Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSecretKey() {
        byte[] keyBytes = SECRET_KEY.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(User user){

        long expirationTime = System.currentTimeMillis() + accessTokenValidity;

        return Jwts.builder()
                .signWith(getSecretKey())
                .claim("username", user.getUsername())
                .expiration(new Date(expirationTime))
                .compact();
    }


}
