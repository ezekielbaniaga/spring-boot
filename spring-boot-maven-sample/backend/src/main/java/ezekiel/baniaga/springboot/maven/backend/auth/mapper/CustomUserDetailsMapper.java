package ezekiel.baniaga.springboot.maven.backend.auth.mapper;

import ezekiel.baniaga.springboot.maven.backend.auth.security.CustomUserDetails;
import ezekiel.baniaga.springboot.maven.backend.auth.security.JWTPrincipal;
import ezekiel.baniaga.springboot.maven.backend.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsMapper {

    public CustomUserDetails toCustomUserDetails(JWTPrincipal principal) {
        return new CustomUserDetails(
            principal.userId(),
            principal.username(),
            null,
            principal.role(),
            true
        );
    }

    public CustomUserDetails toCustomUserDetails(User user) {
        return new CustomUserDetails(
            user.getUniqueId(),
            user.getUsername(),
            user.getPassword(),
            user.getRole().name(),
            user.isEnabled()
        );
    }

}
