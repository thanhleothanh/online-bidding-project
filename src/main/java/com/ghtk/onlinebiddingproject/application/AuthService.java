package com.ghtk.onlinebiddingproject.application;

import com.ghtk.onlinebiddingproject.domain.model.auth.VerificationToken;

public interface AuthService {
    AuthResponse login(String username, String password, String applicationUrl);
    AuthResponse signUp(String username, String password, String name, String email, Integer roleId, String applicationUrl);
    void changePassword(Integer userId, String currentPassword, String newPassword, String confirmedNewPassword);
    String validateToken(String token);
    void resendVerification(Integer profileId, String applicationUrl);
    Object generateJwtCookie();
    Object getCleanJwtCookie();
    void saveVerificationToken(Integer profileId, String token);
    VerificationToken generateNewVerification(Integer verificationTokenId);

    record AuthResponse(Integer id, String username, String name, String email, String imageUrl, String role, String status) {}
}
