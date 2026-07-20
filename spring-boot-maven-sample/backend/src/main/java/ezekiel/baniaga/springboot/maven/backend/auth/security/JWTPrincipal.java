package ezekiel.baniaga.springboot.maven.backend.auth.security;

import java.util.UUID;

public record JWTPrincipal (
    UUID userId,
    String username,
    String role
){}
