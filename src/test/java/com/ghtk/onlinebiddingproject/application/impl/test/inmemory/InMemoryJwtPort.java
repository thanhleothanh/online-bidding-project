package com.ghtk.onlinebiddingproject.application.impl.test.inmemory;

import com.ghtk.onlinebiddingproject.application.port.JwtPort;
import com.ghtk.onlinebiddingproject.domain.model.auth.Profile;

public class InMemoryJwtPort implements JwtPort {

    @Override
    public String generateJwtCookie(Profile profile) {
        return "dummy-jwt-token";
    }

    @Override
    public String getCleanJwtCookie() {
        return "";
    }
}
