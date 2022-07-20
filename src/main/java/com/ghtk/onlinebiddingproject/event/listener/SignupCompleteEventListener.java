package com.ghtk.onlinebiddingproject.event.listener;

import com.ghtk.onlinebiddingproject.event.SignupCompleteEvent;
import com.ghtk.onlinebiddingproject.exceptions.BadRequestException;
import com.ghtk.onlinebiddingproject.exceptions.NotFoundException;
import com.ghtk.onlinebiddingproject.models.dtos.ProfileDto;
import com.ghtk.onlinebiddingproject.models.entities.Profile;
import com.ghtk.onlinebiddingproject.models.entities.User;
import com.ghtk.onlinebiddingproject.services.impl.AuthServiceImpl;
import com.ghtk.onlinebiddingproject.services.impl.ProfileServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class SignupCompleteEventListener implements ApplicationListener<SignupCompleteEvent> {

    @Autowired
    private AuthServiceImpl authService;

    @Override
    public void onApplicationEvent(SignupCompleteEvent event) {
        // Create the verification Token for  ther profile with link
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
