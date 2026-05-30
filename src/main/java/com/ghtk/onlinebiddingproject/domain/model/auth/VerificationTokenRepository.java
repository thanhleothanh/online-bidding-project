package com.ghtk.onlinebiddingproject.domain.model.auth;

import java.util.Optional;

public interface VerificationTokenRepository {
    Optional<VerificationToken> findById(Integer id);
    VerificationToken findByToken(String token);
    VerificationToken findByProfileId(Integer profileId);
    VerificationToken save(VerificationToken verificationToken);
    void delete(VerificationToken verificationToken);
}
