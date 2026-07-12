package ezekiel.baniaga.springboot.maven.backend.auth.security;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

import ezekiel.baniaga.springboot.maven.backend.auth.props.JWTProperties;
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
            .claim("role", user.getAuthorities().iterator().next().getAuthority())
            .issuedAt(Date.from(now))
            .expiration(Date.from(now.plus(jwtProperties.getExpiration())))
            .signWith(signingKey())
            .compact();
    }

    public String extractUsername(String token) {

        return null;
    }

    public boolean isTokenValid(String token) {

        return false;
    }

    public Instant extractExpiration(String token) {

        return null;
    }

    private SecretKey signingKey() {
        return Keys.hmacShaKeyFor(
            jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8)
        );
    }
}
