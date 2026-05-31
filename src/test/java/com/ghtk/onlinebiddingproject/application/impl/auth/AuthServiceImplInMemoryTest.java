package com.ghtk.onlinebiddingproject.application.impl.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.ghtk.onlinebiddingproject.application.impl.test.inmemory.InMemoryMailPort;
import com.ghtk.onlinebiddingproject.application.impl.test.inmemory.factories.TestPortFactory;
import com.ghtk.onlinebiddingproject.application.impl.test.inmemory.factories.TestRepositoryFactory;
import com.ghtk.onlinebiddingproject.domain.model.auth.Profile;
import com.ghtk.onlinebiddingproject.domain.model.auth.Role;
import com.ghtk.onlinebiddingproject.domain.model.auth.UserStatus;
import com.ghtk.onlinebiddingproject.domain.model.auth.VerificationToken;
import org.junit.jupiter.api.Test;

class AuthServiceImplInMemoryTest extends AbstractAuthServiceTest {

    @Override
    protected void initDependencies() {
        userRepository = TestRepositoryFactory.createUserRepository();
        verificationTokenRepository = TestRepositoryFactory.createVerificationTokenRepository();
        mailPort = TestPortFactory.createMailPort();
        jwtPort = TestPortFactory.createJwtPort();
        eventPublisher = TestPortFactory.createEventPublisher();
    }

    @Override
    protected void setUpData() {
        Profile admin = new Profile(1, "admin", "adminPass", "Admin", "admin@test.com", Role.admin());
        admin.setStatus(UserStatus.ACTIVE);
        userRepository.save(admin);
    }

    private InMemoryMailPort mails() {
        return (InMemoryMailPort) mailPort;
    }

    @Test
    void shouldResendVerificationEmail() {
        Profile user = new Profile(2, "john", "pass", "John", "john@test.com", Role.user());
        user.setStatus(UserStatus.INACTIVE);
        userRepository.save(user);

        authService.resendVerification(2, "http://localhost:8080/");

        assertEquals(1, mails().getSentEmails().size());
        assertEquals("john@test.com", mails().getSentEmails().get(0).to());
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
