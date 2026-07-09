package ezekiel.baniaga.springboot.maven.backend.auth.security;

import ezekiel.baniaga.springboot.maven.backend.user.UserRepository;
import ezekiel.baniaga.springboot.maven.backend.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DatabaseUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException(username));

        return new CustomUserDetails(user);
    }
}
