package com.ghtk.onlinebiddingproject.application.impl;

import com.ghtk.onlinebiddingproject.application.AuthService;
import com.ghtk.onlinebiddingproject.application.port.JwtPort;
import com.ghtk.onlinebiddingproject.application.port.MailPort;
import com.ghtk.onlinebiddingproject.domain.event.EventPublisher;
import com.ghtk.onlinebiddingproject.domain.model.auth.*;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailPort mailPort;
    private final JwtPort jwtPort;
    private final EventPublisher eventPublisher;

    @Override
    public AuthResponse login(String username, String password, String applicationUrl) {
        Profile profile = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!profile.canAuthenticate()) {
            if (profile.getStatus() == UserStatus.INACTIVE) {
                VerificationToken vt = verificationTokenRepository.findByProfileId(profile.getId());
                if (vt != null && vt.isExpired()) {
                    resendVerification(profile.getId(), applicationUrl);
                    throw new RuntimeException("Account not verified. New verification email sent.");
                }
                throw new RuntimeException("Account not verified.");
            }
            throw new RuntimeException("Account cannot authenticate");
        }

        return new AuthResponse(
                profile.getId(),
                profile.getUsername(),
                profile.getName(),
                profile.getEmail(),
                profile.getImageUrl(),
                profile.getRole().getName(),
                profile.getStatus().name()
        );
    }

    @Override
    public AuthResponse signUp(String username, String password, String name, String email, Integer roleId, String applicationUrl) {
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already exists");
        }
        if (roleId == 1) {
            throw new RuntimeException("Cannot create admin account");
        }

        Role role = Role.user();
        Profile newProfile = new Profile(null, username, password, name, email, role);
        Profile savedProfile = userRepository.save(newProfile);
        userRepository.insertUser(savedProfile.getId());

        return new AuthResponse(
                savedProfile.getId(),
                savedProfile.getUsername(),
                savedProfile.getName(),
                savedProfile.getEmail(),
                savedProfile.getImageUrl(),
                savedProfile.getRole().getName(),
                savedProfile.getStatus().name()
        );
    }

    @Override
    public void changePassword(Integer userId, String currentPassword, String newPassword, String confirmedNewPassword) {
        Profile profile = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!newPassword.equals(confirmedNewPassword)) {
            throw new RuntimeException("New passwords do not match");
        }

        profile.changePassword(newPassword);
        userRepository.save(profile);
    }

    @Override
    public String validateToken(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if (verificationToken == null) {
            return "invalid";
        }
        if (verificationToken.isExpired()) {
            return "expired";
        }

        Profile profile = userRepository.findById(verificationToken.getProfileId())
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        profile.setStatus(UserStatus.ACTIVE);
        userRepository.save(profile);
        verificationTokenRepository.delete(verificationToken);
        return "valid";
    }

    @Override
    public void resendVerification(Integer profileId, String applicationUrl) {
        Profile profile = userRepository.findById(profileId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        VerificationToken existingToken = verificationTokenRepository.findByProfileId(profileId);
        VerificationToken verificationToken;
        if (existingToken != null) {
            verificationToken = existingToken;
            verificationToken.extend(10);
        } else {
            verificationToken = new VerificationToken(profileId, UUID.randomUUID().toString());
        }
        verificationTokenRepository.save(verificationToken);

        String url = applicationUrl + "verificationSignup?token=" + verificationToken.getToken();
        mailPort.sendVerificationEmail(profile.getEmail(), profile.getName(), profile.getUsername(), url);
    }

    @Override
    public Object generateJwtCookie() {
        return jwtPort.generateJwtCookie(null);
    }

    @Override
    public Object getCleanJwtCookie() {
        return jwtPort.getCleanJwtCookie();
    }

    @Override
    public void saveVerificationToken(Integer profileId, String token) {
        VerificationToken verificationToken = new VerificationToken(profileId, token);
        verificationTokenRepository.save(verificationToken);
    }

    @Override
    public VerificationToken generateNewVerification(Integer verificationTokenId) {
        VerificationToken verificationToken = verificationTokenRepository.findById(verificationTokenId)
                .orElseThrow(() -> new RuntimeException("Verification token not found"));
        String newToken = UUID.randomUUID().toString();
        VerificationToken newVerificationToken = new VerificationToken(
                verificationToken.getId(), verificationToken.getProfileId(),
                newToken, verificationToken.getExpirationTime().plusMinutes(10));
        return verificationTokenRepository.save(newVerificationToken);
    }
}
