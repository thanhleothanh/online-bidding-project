package com.ghtk.onlinebiddingproject.domain.service;

import com.ghtk.onlinebiddingproject.application.impl.test.inmemory.InMemoryUserRepository;
import com.ghtk.onlinebiddingproject.domain.model.auth.Profile;
import com.ghtk.onlinebiddingproject.domain.model.auth.Role;
import com.ghtk.onlinebiddingproject.domain.model.auth.UserStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LegitimateScoreServiceTest {

    private InMemoryUserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new InMemoryUserRepository();
    }

    @Test
    void shouldDeductScore() {
        Profile user = new Profile(1, "john", "pass", "John", "john@test.com", Role.user());
        user.setStatus(UserStatus.ACTIVE);
        user.setLegitimateScore(10);
        userRepository.save(user);

        LegitimateScoreService.deductScore(1, userRepository);

        Profile updated = userRepository.findById(1).orElseThrow();
        assertEquals(5, updated.getLegitimateScore());
    }

    @Test
    void shouldNotDeductBelowZero() {
        Profile user = new Profile(1, "john", "pass", "John", "john@test.com", Role.user());
        user.setStatus(UserStatus.ACTIVE);
        user.setLegitimateScore(3);
        userRepository.save(user);

        LegitimateScoreService.deductScore(1, userRepository);

        Profile updated = userRepository.findById(1).orElseThrow();
        assertEquals(0, updated.getLegitimateScore());
    }
}
