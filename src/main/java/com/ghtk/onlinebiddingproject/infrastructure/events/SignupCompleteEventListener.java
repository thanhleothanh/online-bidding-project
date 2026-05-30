package com.ghtk.onlinebiddingproject.infrastructure.events;

import com.ghtk.onlinebiddingproject.application.AuthService;
import com.ghtk.onlinebiddingproject.domain.model.auth.Profile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SignupCompleteEventListener implements ApplicationListener<SignupCompleteEvent> {

    private static final Logger logger = LoggerFactory.getLogger(SignupCompleteEventListener.class);

    private final AuthService authService;

    public SignupCompleteEventListener(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void onApplicationEvent(SignupCompleteEvent event) {
        Profile profile = event.getProfile();
        String token = UUID.randomUUID().toString();
        authService.saveVerificationToken(profile.getId(), token);

        String url = event.getApplicationEventURL() + "verificationSignup?token=" + token;
        authService.resendVerification(profile.getId(), url);
        logger.info("Signup complete event processed for user: {}", profile.getUsername());
    }
}
