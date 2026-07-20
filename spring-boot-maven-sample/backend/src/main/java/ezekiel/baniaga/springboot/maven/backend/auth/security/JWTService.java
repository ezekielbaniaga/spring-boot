package ezekiel.baniaga.springboot.maven.backend.auth.security;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import ezekiel.baniaga.springboot.maven.backend.auth.props.JWTProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
@RequiredArgsConstructor
public class JWTService {

    private final JWTProperties jwtProperties;

    public String generateToken(CustomUserDetails user) {
        Instant now = Instant.now();

        return Jwts.builder()
            .subject(user.getUsername())
            .claim("uid", user.getUniqueId().toString())
            .claim("role", user.getRole())
            .issuedAt(Date.from(now))
            .expiration(Date.from(now.plus(jwtProperties.getExpiration())))
            .signWith(signingKey())
            .compact();
    }

    public JWTPrincipal extractPrincipal(String token) {

        Claims claims = extractAllClaims(token);

        return new JWTPrincipal(
            UUID.fromString(claims.get("uid", String.class)),
            claims.getSubject(),
            claims.get("role", String.class)
        );
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public boolean isTokenValid(String token) {
        try {
            return !isTokenExpired(token);
        } catch (JwtException ex) {
            return false;
        }
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).isBefore(Instant.now());
    }

    public Instant extractExpiration(String token) {
        return extractAllClaims(token).getExpiration().toInstant();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
            .verifyWith(signingKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

    private SecretKey signingKey() {
        return Keys.hmacShaKeyFor(
            jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8)
        );
    }
}
