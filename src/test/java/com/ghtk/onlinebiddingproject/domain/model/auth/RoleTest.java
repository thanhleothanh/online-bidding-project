package com.ghtk.onlinebiddingproject.domain.model.auth;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

    @Test
    void shouldCreateAdminRole() {
        Role admin = Role.admin();
        assertEquals(1, admin.getId());
        assertEquals("ADMIN", admin.getName());
        assertTrue(admin.isAdmin());
        assertFalse(admin.isUser());
    }

    @Test
    void shouldCreateUserRole() {
        Role user = Role.user();
        assertEquals(2, user.getId());
        assertEquals("USER", user.getName());
        assertTrue(user.isUser());
        assertFalse(user.isAdmin());
    }

    @Test
    void shouldBeEqualWhenSameIdAndName() {
        Role role1 = Role.of(1, "ADMIN");
        Role role2 = Role.of(1, "ADMIN");
        assertEquals(role1, role2);
    }

    @Test
    void shouldNotBeEqualWhenDifferentId() {
        Role role1 = Role.of(1, "ADMIN");
        Role role2 = Role.of(2, "ADMIN");
        assertNotEquals(role1, role2);
    }
}
