package com.ghtk.onlinebiddingproject.infrastructure.events;

import com.ghtk.onlinebiddingproject.domain.model.auth.Profile;
import org.springframework.context.ApplicationEvent;

public class SignupCompleteEvent extends ApplicationEvent {

    private final Profile profile;
    private final String applicationEventURL;

    public SignupCompleteEvent(Profile profile, String applicationEventURL) {
        super(profile);
        this.profile = profile;
        this.applicationEventURL = applicationEventURL;
    }

    public Profile getProfile() {
        return profile;
    }

    public String getApplicationEventURL() {
        return applicationEventURL;
    }
}
