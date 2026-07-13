package ezekiel.baniaga.springboot.maven.backend.auth;

import ezekiel.baniaga.springboot.maven.backend.auth.security.CustomUserDetails;
import ezekiel.baniaga.springboot.maven.backend.auth.security.JWTService;
import ezekiel.baniaga.springboot.maven.backend.user.UserRepository;
import ezekiel.baniaga.springboot.maven.backend.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//@RestController
//@RequestMapping("/debug")
@Deprecated
@RequiredArgsConstructor
public class DebugController {

    private final JWTService jwtService;

    private final UserRepository userRepository;

    @GetMapping("/token")
    public String token() {
        User user = userRepository.findByUsername("admin").orElseThrow();
        return jwtService.generateToken(new CustomUserDetails(user));
    }

    @GetMapping("/validate")
    public boolean validate(@RequestParam String token) {
        return jwtService.isTokenValid(token);
    }
}
