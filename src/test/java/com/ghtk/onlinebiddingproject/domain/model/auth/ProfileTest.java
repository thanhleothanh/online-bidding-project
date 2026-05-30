package com.ghtk.onlinebiddingproject.domain.model.auth;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProfileTest {

    @Test
    void shouldChangePassword() {
        Profile profile = new Profile(1, "john", "oldPass", "John", "john@test.com", Role.user());
        profile.changePassword("newPass");
        assertEquals("newPass", profile.getPassword());
    }

    @Test
    void shouldUpdateProfile() {
        Profile profile = new Profile(1, "john", "pass", "John", "john@test.com", Role.user());
        profile.updateProfile("Jane", "New bio", "http://new-image.jpg");
        assertEquals("Jane", profile.getName());
        assertEquals("New bio", profile.getBio());
        assertEquals("http://new-image.jpg", profile.getImageUrl());
    }

    @Test
    void shouldDeductLegitimateScore() {
        Profile profile = new Profile(1, "john", "pass", "John", "john@test.com", Role.user());
        profile.addLegitimateScore(10);
        profile.deductLegitimateScore(3);
        assertEquals(7, profile.getLegitimateScore());
    }

    @Test
    void shouldAddLegitimateScore() {
        Profile profile = new Profile(1, "john", "pass", "John", "john@test.com", Role.user());
        profile.addLegitimateScore(5);
        assertEquals(5, profile.getLegitimateScore());
    }

    @Test
    void shouldCheckSuspendedStatus() {
        Profile profile = new Profile(1, "john", "pass", "John", "john@test.com", Role.user());
        profile.setStatus(UserStatus.SUSPENDED);
        assertTrue(profile.isSuspended());
        assertFalse(profile.isActive());
    }

    @Test
    void shouldCheckActiveStatus() {
        Profile profile = new Profile(1, "john", "pass", "John", "john@test.com", Role.user());
        profile.setStatus(UserStatus.ACTIVE);
        assertTrue(profile.isActive());
        assertFalse(profile.isSuspended());
        assertTrue(profile.canAuthenticate());
    }

    @Test
    void shouldNotAuthenticateWhenInactive() {
        Profile profile = new Profile(1, "john", "pass", "John", "john@test.com", Role.user());
        profile.setStatus(UserStatus.INACTIVE);
        assertFalse(profile.canAuthenticate());
    }

    @Test
    void shouldBeEqualById() {
        Profile profile1 = new Profile(1, "john", "pass", "John", "john@test.com", Role.user());
        Profile profile2 = new Profile(1, "jane", "pass", "Jane", "jane@test.com", Role.user());
        assertEquals(profile1, profile2);
    }
}
