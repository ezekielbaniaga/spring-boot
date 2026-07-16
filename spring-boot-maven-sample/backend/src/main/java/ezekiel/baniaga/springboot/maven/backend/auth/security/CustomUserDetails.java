package ezekiel.baniaga.springboot.maven.backend.auth.security;

import ezekiel.baniaga.springboot.maven.backend.user.entity.Role;
import ezekiel.baniaga.springboot.maven.backend.user.entity.User;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class CustomUserDetails implements UserDetails {

    private final User user;
    private final JWTPrincipal principal;

    private final UUID uniqueId;
    private final String username;
    private final String password;
    private final Role role;
    private final boolean enabled;

    public CustomUserDetails(User user) {
       this.user = user;
       this.uniqueId = user.getUniqueId();
       this.username = user.getUsername();
       this.password = user.getPassword();
       this.role = user.getRole();
       this.enabled = user.isEnabled();
    }

    public CustomUserDetails(JWTPrincipal principal) {
        this.principal = principal;
        this.uniqueId = principal.userId();
        this.username = principal.username();
        this.role = principal.role();
        this.enabled = principal.enabled();
    }

    public User getUser() {
        return this.user;
    }

    public UUID getUniqueId() {
        return this.uniqueId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.role.name()));
    }

    @Override
    public @Nullable String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
