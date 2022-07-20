package com.ghtk.onlinebiddingproject.event;

import com.ghtk.onlinebiddingproject.models.dtos.ProfileDto;
import com.ghtk.onlinebiddingproject.models.entities.Profile;
import com.ghtk.onlinebiddingproject.models.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

@Getter
@Setter
public class SignupCompleteEvent extends ApplicationEvent {

    private Profile profile;
    private String applicationEventURL;

    public SignupCompleteEvent(Profile profile, String applicationEventURL) {
        super(profile);
        this.profile = profile;
        this.applicationEventURL = applicationEventURL;
    }

}
