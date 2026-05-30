package com.ghtk.onlinebiddingproject.domain.model.auth;

public enum UserStatus {
    BANNED,
    SUSPENDED,
    ACTIVE,
    INACTIVE;

    public boolean isActive() {
        return this == ACTIVE;
    }

    public boolean isSuspended() {
        return this == SUSPENDED;
    }

    public boolean canAuthenticate() {
        return this == ACTIVE;
    }

    public boolean isBanned() {
        return this == BANNED;
    }
}
