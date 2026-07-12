package ezekiel.baniaga.springboot.maven.backend.auth.props;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "app.jwt")
@Getter @Setter
public class JWTProperties {

    /**
     * At least 256 bits (32 bytes) for HS256.
     * Generate secret from https://jwtgenerator.com/tools/jwt-generator
     */
    private String secret;

    /**
     * Spring Boot can bind "15m", "1h", etc.
     */
    private Duration expiration = Duration.ofMinutes(15);

}
