package com.ganatan.backend_java.modules.users.security.jwt;

import com.ganatan.backend_java.modules.users.entities.User;
import com.ganatan.backend_java.modules.users.security.UserDetails.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    @Value("${application.security.jwt.expiration}")
    public long EXPIRATION_TIME;

    @Value("${application.security.jwt.secret-key}")
    private String SECRET_KEY;

    public String generateToken(User user) {
        return buildToken(user, EXPIRATION_TIME);
    }

    private String buildToken(User user, long expiration) {
        return Jwts.builder()
                .setId(user.getId().toString())
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey())
                .compact();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String refreshToken) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(refreshToken)
                .getBody();

        return claims.getSubject();
    }

    public String extractId(String refreshToken) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(refreshToken)
                .getBody();

        return claims.getId();
    }

    public Long extractId(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userDetails.getUser().getId();
    }

    public String extractUsername(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userDetails.getUser().getEmail();
    }

    public boolean isTokenValid(String token, User user) {
        final String username = extractUsername(token);
        return (username.equals(user.getEmail())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String refreshToken) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(refreshToken)
                .getBody();

        return claims.getExpiration();
    }
}
