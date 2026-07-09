package ezekiel.baniaga.springboot.maven.backend.auth.service;

import ezekiel.baniaga.springboot.maven.backend.auth.config.AdminBootstrapProperties;
import ezekiel.baniaga.springboot.maven.backend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminBootstrapService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AdminBootstrapProperties adminBootstrapProperties;

}
