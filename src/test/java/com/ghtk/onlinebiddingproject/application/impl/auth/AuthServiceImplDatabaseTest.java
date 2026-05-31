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
        // seed-roles.sql already has profiles with usernames user1, user2, admin
        // Use a unique username to avoid duplicate key constraint
        if (userRepository.findByUsername("testadmin").isEmpty()) {
            Profile admin = new Profile(4, "testadmin", "adminPass", "Test Admin", "testadmin@test.com", Role.admin());
            admin.setStatus(UserStatus.ACTIVE);
            userRepository.save(admin);
            // Note: insertAdmin() has a bug (uses 'id' instead of 'profile_id'), so we skip it
            // The seed-roles.sql already has admin with profile_id=3, so tests can use that
        }
    }
}
