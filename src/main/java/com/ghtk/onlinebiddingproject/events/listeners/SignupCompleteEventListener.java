package com.ghtk.onlinebiddingproject.events.listeners;

import com.ghtk.onlinebiddingproject.events.SignupCompleteEvent;
import com.ghtk.onlinebiddingproject.models.entities.Profile;
import com.ghtk.onlinebiddingproject.services.impl.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SignupCompleteEventListener implements ApplicationListener<SignupCompleteEvent> {

    private final AuthServiceImpl authService;

    @Override
    public void onApplicationEvent(SignupCompleteEvent event) {
        Profile profile = event.getProfile();
        String token = UUID.randomUUID().toString();
        authService.saveVerificationTokenForProfile(token, profile);
        // Send Mail to user
        String url = event.getApplicationEventURL()
                + "verificationSignup?token="
                + token;
        authService.sendMailVerification(profile, url);
    }
}
