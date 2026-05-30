package com.ghtk.onlinebiddingproject.domain.model.auth;

public class Admin extends Profile {

    public Admin(Integer id, String username, String password, String name, String email) {
        super(id, username, password, name, email, Role.admin());
    }

    public Admin(Integer id) {
        super(id);
    }

    public boolean canApproveAuction() {
        return true;
    }

    public boolean canBanUser(Integer targetId) {
        return !getId().equals(targetId);
    }

    public boolean canJudgeReport() {
        return true;
    }

    public boolean canManageUsers() {
        return true;
    }
}
