package com.rumay.pruebas_customer.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    // Generar clave de firma
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    // Generar token JWT
    public String generarToken(String username, String nombre) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("nombre", nombre);

        return Jwts.builder()
                .claims(claims)  // CAMBIADO: usar claims() en vez de setClaims()
                .subject(username)  // CAMBIADO: usar subject() en vez de setSubject()
                .issuedAt(new Date(System.currentTimeMillis()))  // CAMBIADO
                .expiration(new Date(System.currentTimeMillis() + expiration))  // CAMBIADO
                .signWith(getSigningKey())  // CAMBIADO: no necesita SignatureAlgorithm
                .compact();
    }

    // Extraer username del token
    public String extraerUsername(String token) {
        return extraerClaim(token, Claims::getSubject);
    }

    // Extraer nombre del token
    public String extraerNombre(String token) {
        return extraerClaim(token, claims -> claims.get("nombre", String.class));
    }

    // Extraer un claim específico
    public <T> T extraerClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extraerTodosClaims(token);
        return claimsResolver.apply(claims);
    }

    // CORREGIDO: Extraer todos los claims del token (JJWT 0.12.x)
    private Claims extraerTodosClaims(String token) {
        return Jwts.parser()  // CAMBIADO: usar parser() directamente
                .verifyWith(getSigningKey())  // CAMBIADO: usar verifyWith() en vez de setSigningKey()
                .build()
                .parseSignedClaims(token)  // CAMBIADO: usar parseSignedClaims() en vez de parseClaimsJws()
                .getPayload();  // CAMBIADO: usar getPayload() en vez de getBody()
    }

    // Validar token
    public boolean validarToken(String token, String username) {
        try {
            final String tokenUsername = extraerUsername(token);
            return (tokenUsername.equals(username) && !esTokenExpirado(token));
        } catch (Exception e) {
            return false;
        }
    }

    // Verificar si el token está expirado
    private boolean esTokenExpirado(String token) {
        return extraerExpiracion(token).before(new Date());
    }

    // Extraer fecha de expiración
    private Date extraerExpiracion(String token) {
        return extraerClaim(token, Claims::getExpiration);
    }
}