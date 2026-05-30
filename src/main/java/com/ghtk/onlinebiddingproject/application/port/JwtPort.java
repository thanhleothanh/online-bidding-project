package com.ghtk.onlinebiddingproject.application.port;

import com.ghtk.onlinebiddingproject.domain.model.auth.Profile;

public interface JwtPort {
    Object generateJwtCookie(Profile profile);
    Object getCleanJwtCookie();
}
