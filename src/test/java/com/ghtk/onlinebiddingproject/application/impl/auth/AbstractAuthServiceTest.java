package com.ghtk.onlinebiddingproject.application.impl.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.ghtk.onlinebiddingproject.application.impl.AuthServiceImpl;
import com.ghtk.onlinebiddingproject.application.port.JwtPort;
import com.ghtk.onlinebiddingproject.application.port.MailPort;
import com.ghtk.onlinebiddingproject.domain.event.EventPublisher;
import com.ghtk.onlinebiddingproject.domain.model.auth.Profile;
import com.ghtk.onlinebiddingproject.domain.model.auth.Role;
import com.ghtk.onlinebiddingproject.domain.model.auth.UserRepository;
import com.ghtk.onlinebiddingproject.domain.model.auth.UserStatus;
import com.ghtk.onlinebiddingproject.domain.model.auth.VerificationToken;
import com.ghtk.onlinebiddingproject.domain.model.auth.VerificationTokenRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public abstract class AbstractAuthServiceTest {

    protected UserRepository userRepository;
    protected VerificationTokenRepository verificationTokenRepository;
    protected MailPort mailPort;
    protected JwtPort jwtPort;
    protected EventPublisher eventPublisher;
    protected AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        initDependencies();
        authService = new AuthServiceImpl(userRepository, verificationTokenRepository, mailPort, jwtPort, eventPublisher);
        setUpData();
    }

    protected abstract void initDependencies();
    protected abstract void setUpData();

    @Test
    void shouldLoginActiveUser() {
        Profile user = new Profile(2, "john", "johnPass", "John", "john@test.com", Role.user());
        user.setStatus(UserStatus.ACTIVE);
        userRepository.save(user);

        var response = authService.login("john", "johnPass", "http://localhost:8080/");

        assertEquals(2, response.id());
        assertEquals("john", response.username());
        assertEquals("ACTIVE", response.status());
    }

    @Test
    void shouldRejectLoginForInactiveUser() {
        Profile user = new Profile(2, "john", "johnPass", "John", "john@test.com", Role.user());
        user.setStatus(UserStatus.INACTIVE);
        userRepository.save(user);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> authService.login("john", "johnPass", "http://localhost:8080/"));
        assertEquals("Account not verified.", exception.getMessage());
    }

    @Test
    void shouldRejectLoginForSuspendedUser() {
        Profile user = new Profile(2, "john", "johnPass", "John", "john@test.com", Role.user());
        user.setStatus(UserStatus.SUSPENDED);
        userRepository.save(user);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> authService.login("john", "johnPass", "http://localhost:8080/"));
        assertEquals("Account cannot authenticate", exception.getMessage());
    }

    @Test
    void shouldRejectLoginForBannedUser() {
        Profile user = new Profile(2, "john", "johnPass", "John", "john@test.com", Role.user());
        user.setStatus(UserStatus.BANNED);
        userRepository.save(user);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> authService.login("john", "johnPass", "http://localhost:8080/"));
        assertEquals("Account cannot authenticate", exception.getMessage());
    }

    @Test
    void shouldRejectLoginForNonExistentUser() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> authService.login("nonexistent", "pass", "http://localhost:8080/"));
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void shouldSignUpNewUser() {
        var response = authService.signUp("jane", "janePass", "Jane", "jane@test.com", 2, "http://localhost:8080/");

        assertNotNull(response.id());
        assertEquals("jane", response.username());
        assertEquals("Jane", response.name());
        assertEquals("jane@test.com", response.email());
        assertEquals("USER", response.role());
        assertEquals("INACTIVE", response.status());
    }

    @Test
    void shouldRejectSignUpForDuplicateUsername() {
        Profile existing = new Profile(1, "john", "pass", "John", "john@test.com", Role.user());
        userRepository.save(existing);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> authService.signUp("john", "pass", "John2", "john2@test.com", 2, "http://localhost:8080/"));
        assertEquals("Username already exists", exception.getMessage());
    }

    @Test
    void shouldRejectSignUpForDuplicateEmail() {
        Profile existing = new Profile(1, "john", "pass", "John", "john@test.com", Role.user());
        userRepository.save(existing);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> authService.signUp("john2", "pass", "John2", "john@test.com", 2, "http://localhost:8080/"));
        assertEquals("Email already exists", exception.getMessage());
    }

    @Test
    void shouldRejectSignUpForAdminRole() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> authService.signUp("admin2", "pass", "Admin2", "admin2@test.com", 1, "http://localhost:8080/"));
        assertEquals("Cannot create admin account", exception.getMessage());
    }

    @Test
    void shouldChangePassword() {
        Profile user = new Profile(2, "john", "oldPass", "John", "john@test.com", Role.user());
        user.setStatus(UserStatus.ACTIVE);
        userRepository.save(user);

        authService.changePassword(2, "oldPass", "newPass", "newPass");

        Profile updated = userRepository.findById(2).orElseThrow();
        assertEquals("newPass", updated.getPassword());
    }

    @Test
    void shouldRejectPasswordChangeWhenPasswordsDontMatch() {
        Profile user = new Profile(2, "john", "oldPass", "John", "john@test.com", Role.user());
        user.setStatus(UserStatus.ACTIVE);
        userRepository.save(user);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> authService.changePassword(2, "oldPass", "newPass", "differentPass"));
        assertEquals("New passwords do not match", exception.getMessage());
    }

    @Test
    void shouldValidateValidToken() {
        Profile user = new Profile(2, "john", "pass", "John", "john@test.com", Role.user());
        user.setStatus(UserStatus.INACTIVE);
        userRepository.save(user);

        VerificationToken token = new VerificationToken(2, "valid-token-123");
        verificationTokenRepository.save(token);

        String result = authService.validateToken("valid-token-123");

        assertEquals("valid", result);
        Profile updated = userRepository.findById(2).orElseThrow();
        assertEquals(UserStatus.ACTIVE, updated.getStatus());
    }

    @Test
    void shouldRejectExpiredToken() {
        Profile user = new Profile(2, "john", "pass", "John", "john@test.com", Role.user());
        user.setStatus(UserStatus.INACTIVE);
        userRepository.save(user);

        VerificationToken token = new VerificationToken(2, 2, "expired-token",
                LocalDateTime.now().minusMinutes(1));
        verificationTokenRepository.save(token);

        String result = authService.validateToken("expired-token");

        assertEquals("expired", result);
    }

    @Test
    void shouldRejectInvalidToken() {
        String result = authService.validateToken("nonexistent-token");

        assertEquals("invalid", result);
    }

    @Test
    void shouldSaveVerificationToken() {
        Profile user = new Profile(2, "john", "pass", "John", "john@test.com", Role.user());
        userRepository.save(user);

        authService.saveVerificationToken(2, "token-123");

        VerificationToken saved = verificationTokenRepository.findByProfileId(2);
        assertNotNull(saved);
        assertEquals("token-123", saved.getToken());
    }
}
