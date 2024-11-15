package com.tienda.facil.core.service.impl;

import com.tienda.facil.core.model.Cliente;
import com.tienda.facil.core.service.IJwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService implements IJwtService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;


    @Override
    public String generateToken(Cliente cliente) {
        return buildToken(cliente, jwtExpiration);
    }

    @Override
    public String generateRefreshToken(Cliente cliente) {
        return buildToken(cliente, refreshExpiration);
    }

    @Override
    public String extractUsername(String token) {
        final Claims jwtToken = Jwts.parser()
                .verifyWith(getSingInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return jwtToken.getSubject();
    }

    @Override
    public boolean isTokenValid(String token, Cliente cliente) {
        final String username = this.extractUsername(token);
        return (username.equals(cliente.getEmail())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        final Claims jwtToken = Jwts.parser()
                .verifyWith(getSingInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return jwtToken.getExpiration();
    }

    private String buildToken(final Cliente cliente, long expiration) {
        var roles = cliente.getRoles().stream()
                .map(role -> "ROLE_" + role.name())
                .toList();
        return Jwts.builder()
                .id(cliente.getId().toString())
                .claims(Map.of(
                        "name", cliente.getNombre(),
                        "roles", roles
                ))
                .subject(cliente.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSingInKey())
                .compact();

    }

    private SecretKey getSingInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
