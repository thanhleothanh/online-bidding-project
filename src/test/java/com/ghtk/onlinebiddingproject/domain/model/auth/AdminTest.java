package com.ghtk.onlinebiddingproject.domain.model.auth;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdminTest {

    @Test
    void shouldAlwaysAllowApproveAuction() {
        Admin admin = new Admin(1, "admin", "pass", "Admin", "admin@test.com");
        assertTrue(admin.canApproveAuction());
    }

    @Test
    void shouldAllowBanOtherUser() {
        Admin admin = new Admin(1, "admin", "pass", "Admin", "admin@test.com");
        assertTrue(admin.canBanUser(2));
    }

    @Test
    void shouldNotAllowBanSelf() {
        Admin admin = new Admin(1, "admin", "pass", "Admin", "admin@test.com");
        assertFalse(admin.canBanUser(1));
    }

    @Test
    void shouldAlwaysAllowJudgeReport() {
        Admin admin = new Admin(1, "admin", "pass", "Admin", "admin@test.com");
        assertTrue(admin.canJudgeReport());
    }

    @Test
    void shouldAlwaysAllowManageUsers() {
        Admin admin = new Admin(1, "admin", "pass", "Admin", "admin@test.com");
        assertTrue(admin.canManageUsers());
    }
}
