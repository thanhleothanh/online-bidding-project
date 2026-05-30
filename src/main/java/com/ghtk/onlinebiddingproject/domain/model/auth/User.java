package com.ghtk.onlinebiddingproject.domain.model.auth;

public class User extends Profile {

    public User(Integer id, String username, String password, String name, String email) {
        super(id, username, password, name, email, Role.user());
    }

    public User(Integer id) {
        super(id);
    }

    public boolean canBidOn(Integer auctionOwnerId) {
        return isActive() && !getId().equals(auctionOwnerId);
    }

    public boolean canCreateAuction() {
        return !isSuspended();
    }

    public boolean canReport() {
        return !isSuspended();
    }

    public boolean canWatchAuction() {
        return isActive();
    }
}
