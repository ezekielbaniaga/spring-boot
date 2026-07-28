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

    /**
     * Creates a new session and saves to db for
     * controlling session later like revocation
     *
     * Note: Raw refresh token is returned but
     * never saved to db for security.
     *
        rawRefreshToken
           │
           ├──▶ Client
           │
           └──▶ NEVER database
     */
    public String createSession(
            User user,
            String userAgent,
            String ipAddress) {

        String rawRefreshToken = refreshTokenGenerator.generate();
        String hashedRefreshToken = refreshTokenHasher.hash(rawRefreshToken);
        LocalDateTime now = LocalDateTime.now();

        UserSession session = new UserSession();
        session.setUser(user);
        session.setRefreshTokenHash(hashedRefreshToken);
        session.setUserAgent(userAgent);
        session.setIpAddress(ipAddress);
        session.setCreatedAt(now);
        session.setLastUsedAt(now);
        session.setExpiresAt(now.plusDays(30));

        repository.save(session);

        return rawRefreshToken;
    }
}
