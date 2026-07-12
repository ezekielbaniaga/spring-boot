package ezekiel.baniaga.springboot.maven.backend.auth.service;

import ezekiel.baniaga.springboot.maven.backend.auth.props.JWTProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JWTBootstrapService {

    private final JWTProperties properties;

    public void checkEnvironmentVariable() {

        if (properties.getSecret().isBlank()) {
            throw new IllegalStateException(
                "JWT_SECRET environment variable is required."
            );
        }
    }
}
