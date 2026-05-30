package com.ghtk.onlinebiddingproject.domain.model.auth;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class VerificationTokenTest {

    @Test
    void shouldDetectExpiredToken() {
        VerificationToken token = new VerificationToken(1, 1, "abc", LocalDateTime.now().minusMinutes(5));
        assertTrue(token.isExpired());
    }

    @Test
    void shouldDetectValidToken() {
        VerificationToken token = new VerificationToken(1, 1, "abc", LocalDateTime.now().plusMinutes(5));
        assertFalse(token.isExpired());
    }

    @Test
    void shouldExtendToken() {
        VerificationToken token = new VerificationToken(1, 1, "abc", LocalDateTime.now().plusMinutes(5));
        token.extend(10);
        assertFalse(token.isExpired());
    }

    @Test
    void shouldHaveValidTokenWhenNotExpired() {
        VerificationToken token = new VerificationToken(1, 1, "abc", LocalDateTime.now().plusMinutes(5));
        assertTrue(token.hasValidToken());
    }

    @Test
    void shouldNotHaveValidTokenWhenExpired() {
        VerificationToken token = new VerificationToken(1, 1, "abc", LocalDateTime.now().minusMinutes(5));
        assertFalse(token.hasValidToken());
    }

    @Test
    void shouldNotHaveValidTokenWhenNull() {
        VerificationToken token = new VerificationToken(1, 1, null, LocalDateTime.now().plusMinutes(5));
        assertFalse(token.hasValidToken());
    }
}
