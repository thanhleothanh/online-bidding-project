package com.ghtk.onlinebiddingproject.application.impl;

import com.ghtk.onlinebiddingproject.application.AuthService;
import com.ghtk.onlinebiddingproject.application.impl.inmemory.*;
import com.ghtk.onlinebiddingproject.domain.model.auth.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AuthServiceImplTest {

    private AuthServiceImpl authService;
    private InMemoryUserRepository userRepository;
    private InMemoryVerificationTokenRepository verificationTokenRepository;
    private InMemoryMailPort mailPort;
    private InMemoryJwtPort jwtPort;
    private InMemoryEventPublisher eventPublisher;

    @BeforeEach
    void setUp() {
        userRepository = new InMemoryUserRepository();
        verificationTokenRepository = new InMemoryVerificationTokenRepository();
        mailPort = new InMemoryMailPort();
        jwtPort = new InMemoryJwtPort();
        eventPublisher = new InMemoryEventPublisher();

        authService = new AuthServiceImpl(userRepository, verificationTokenRepository, mailPort, jwtPort, eventPublisher);

        Profile admin = new Profile(1, "admin", "adminPass", "Admin", "admin@test.com", Role.admin());
        admin.setStatus(UserStatus.ACTIVE);
        userRepository.save(admin);
    }

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
    void shouldResendVerificationEmail() {
        Profile user = new Profile(2, "john", "pass", "John", "john@test.com", Role.user());
        user.setStatus(UserStatus.INACTIVE);
        userRepository.save(user);

        authService.resendVerification(2, "http://localhost:8080/");

        assertEquals(1, mailPort.getSentEmails().size());
        assertEquals("john@test.com", mailPort.getSentEmails().get(0).to());
    }

    @Test
    void shouldGenerateJwtCookie() {
        Object cookie = authService.generateJwtCookie();

        assertNotNull(cookie);
    }

    @Test
    void shouldGetCleanJwtCookie() {
        Object cookie = authService.getCleanJwtCookie();

        assertNotNull(cookie);
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

    @Test
    void shouldGenerateNewVerification() {
        Profile user = new Profile(2, "john", "pass", "John", "john@test.com", Role.user());
        userRepository.save(user);

        VerificationToken oldToken = new VerificationToken(2, "old-token");
        VerificationToken saved = verificationTokenRepository.save(oldToken);

        VerificationToken newToken = authService.generateNewVerification(saved.getId());

        assertNotNull(newToken);
        assertNotEquals("old-token", newToken.getToken());
    }
}
