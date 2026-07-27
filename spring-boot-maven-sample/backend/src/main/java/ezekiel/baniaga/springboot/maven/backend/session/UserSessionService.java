package ezekiel.baniaga.springboot.maven.backend.session;

import ezekiel.baniaga.springboot.maven.backend.session.entity.UserSession;
import ezekiel.baniaga.springboot.maven.backend.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserSessionService {

    private final UserSessionRepository repository;
    private final RefreshTokenGenerator refreshTokenGenerator;
    private final RefreshTokenHasher refreshTokenHasher;

    public String createSession(User user) {
        String rawRefreshToken = refreshTokenGenerator.generate();
        String hashedRefreshToken = refreshTokenHasher.hash(rawRefreshToken);
        LocalDateTime now = LocalDateTime.now();

        UserSession session = new UserSession();
        session.setUser(user);
        session.setRefreshTokenHash(hashedRefreshToken);
        session.setCreatedAt(now);
        session.setExpiresAt(now.plusDays(30));

        repository.save(session);

        return rawRefreshToken;
    }
}
