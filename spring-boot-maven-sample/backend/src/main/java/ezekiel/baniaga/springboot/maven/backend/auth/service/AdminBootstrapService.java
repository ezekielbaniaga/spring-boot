package ezekiel.baniaga.springboot.maven.backend.auth.service;

import ezekiel.baniaga.springboot.maven.backend.auth.props.AdminBootstrapProperties;
import ezekiel.baniaga.springboot.maven.backend.config.DefaultUUIDGenerator;
import ezekiel.baniaga.springboot.maven.backend.user.UserRepository;
import ezekiel.baniaga.springboot.maven.backend.user.entity.Role;
import ezekiel.baniaga.springboot.maven.backend.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AdminBootstrapService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AdminBootstrapProperties adminBootstrapProperties;
    private final DefaultUUIDGenerator uuidGenerator;

    public void createInitialAdminUser() {
        if (adminBootstrapProperties.getPassword().isBlank()) {
            throw new IllegalStateException(
                "ADMIN_PASSWORD environment variable is required."
            );
        }

        if (userRepository.findByUsername(adminBootstrapProperties.getUsername()).isPresent()) {
            return;
        }

        User admin = new User();
        admin.setUniqueId(uuidGenerator.generate());
        admin.setUsername(adminBootstrapProperties.getUsername());
        admin.setPassword(passwordEncoder.encode(adminBootstrapProperties.getPassword()));
        admin.setRole(Role.ADMIN);
        admin.setEnabled(true);
        admin.setCreatedAt(LocalDateTime.now());

        userRepository.save(admin);
    }
}
