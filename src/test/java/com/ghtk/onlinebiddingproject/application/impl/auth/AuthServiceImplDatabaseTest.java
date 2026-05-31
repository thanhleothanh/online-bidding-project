package com.ghtk.onlinebiddingproject.application.impl.auth;

import com.ghtk.onlinebiddingproject.domain.model.auth.Profile;
import com.ghtk.onlinebiddingproject.domain.model.auth.Role;
import com.ghtk.onlinebiddingproject.domain.model.auth.UserRepository;
import com.ghtk.onlinebiddingproject.domain.model.auth.UserStatus;
import com.ghtk.onlinebiddingproject.domain.model.auth.VerificationTokenRepository;
import com.ghtk.onlinebiddingproject.infrastructure.IntegrationDatabaseTest;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationDatabaseTest
class AuthServiceImplDatabaseTest extends AbstractAuthServiceTest {

    @Autowired
    private UserRepository autUserRepository;

    @Autowired
    private VerificationTokenRepository autVerificationTokenRepository;

    @Override
    protected void initDependencies() {
        userRepository = autUserRepository;
        verificationTokenRepository = autVerificationTokenRepository;
        mailPort = null;
        jwtPort = null;
        eventPublisher = null;
    }

    @Override
    protected void setUpData() {
        Profile admin = new Profile(1, "admin", "adminPass", "Admin", "admin@test.com", Role.admin());
        admin.setStatus(UserStatus.ACTIVE);
        userRepository.save(admin);
    }
}
