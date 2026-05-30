package com.ghtk.onlinebiddingproject.adapter.out.security;

import com.ghtk.onlinebiddingproject.application.port.JwtPort;
import com.ghtk.onlinebiddingproject.domain.model.auth.Profile;
import com.ghtk.onlinebiddingproject.infrastructure.security.JwtUtils;
import org.springframework.stereotype.Component;

@Component
public class JwtAdapter implements JwtPort {

    private final JwtUtils jwtUtils;

    public JwtAdapter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public String generateJwtCookie(Profile profile) {
        if (profile == null) return null;
        return jwtUtils.generateTokenFromUsername(profile.getUsername());
    }

    @Override
    public String getCleanJwtCookie() {
        return jwtUtils.getCleanJwtCookie().getValue();
    }
}
