package com.ghtk.onlinebiddingproject.domain.model.auth;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void shouldAllowBidOnOtherUserAuction() {
        User user = new User(1, "john", "pass", "John", "john@test.com");
        user.setStatus(UserStatus.ACTIVE);
        assertTrue(user.canBidOn(2));
    }

    @Test
    void shouldNotAllowBidOnOwnAuction() {
        User user = new User(1, "john", "pass", "John", "john@test.com");
        user.setStatus(UserStatus.ACTIVE);
        assertFalse(user.canBidOn(1));
    }

    @Test
    void shouldNotAllowBidWhenSuspended() {
        User user = new User(1, "john", "pass", "John", "john@test.com");
        user.setStatus(UserStatus.SUSPENDED);
        assertFalse(user.canBidOn(2));
    }

    @Test
    void shouldAllowCreateAuctionWhenActive() {
        User user = new User(1, "john", "pass", "John", "john@test.com");
        user.setStatus(UserStatus.ACTIVE);
        assertTrue(user.canCreateAuction());
    }

    @Test
    void shouldNotAllowCreateAuctionWhenSuspended() {
        User user = new User(1, "john", "pass", "John", "john@test.com");
        user.setStatus(UserStatus.SUSPENDED);
        assertFalse(user.canCreateAuction());
    }

    @Test
    void shouldAllowReportWhenActive() {
        User user = new User(1, "john", "pass", "John", "john@test.com");
        user.setStatus(UserStatus.ACTIVE);
        assertTrue(user.canReport());
    }

    @Test
    void shouldNotAllowReportWhenSuspended() {
        User user = new User(1, "john", "pass", "John", "john@test.com");
        user.setStatus(UserStatus.SUSPENDED);
        assertFalse(user.canReport());
    }

    @Test
    void shouldAllowWatchAuctionWhenActive() {
        User user = new User(1, "john", "pass", "John", "john@test.com");
        user.setStatus(UserStatus.ACTIVE);
        assertTrue(user.canWatchAuction());
    }

    @Test
    void shouldNotAllowWatchAuctionWhenInactive() {
        User user = new User(1, "john", "pass", "John", "john@test.com");
        user.setStatus(UserStatus.INACTIVE);
        assertFalse(user.canWatchAuction());
    }
}
