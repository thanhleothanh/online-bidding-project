package com.ghtk.onlinebiddingproject.application.impl.inmemory;

import com.ghtk.onlinebiddingproject.domain.model.auth.VerificationToken;
import com.ghtk.onlinebiddingproject.domain.model.auth.VerificationTokenRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryVerificationTokenRepository implements VerificationTokenRepository {

    private final Map<Integer, VerificationToken> tokens = new HashMap<>();
    private final Map<Integer, VerificationToken> byProfileId = new HashMap<>();
    private final Map<String, VerificationToken> byToken = new HashMap<>();
    private int nextId = 1;

    @Override
    public Optional<VerificationToken> findById(Integer id) {
        return Optional.ofNullable(tokens.get(id));
    }

    @Override
    public VerificationToken findByProfileId(Integer profileId) {
        return byProfileId.get(profileId);
    }

    @Override
    public VerificationToken findByToken(String token) {
        return byToken.get(token);
    }

    @Override
    public VerificationToken save(VerificationToken verificationToken) {
        if (verificationToken.getId() == null) {
            verificationToken = new VerificationToken(nextId++, verificationToken.getProfileId(),
                    verificationToken.getToken(), verificationToken.getExpirationTime());
        }
        tokens.put(verificationToken.getId(), verificationToken);
        byProfileId.put(verificationToken.getProfileId(), verificationToken);
        byToken.put(verificationToken.getToken(), verificationToken);
        return verificationToken;
    }

    @Override
    public void delete(VerificationToken verificationToken) {
        tokens.remove(verificationToken.getId());
        byProfileId.remove(verificationToken.getProfileId());
        byToken.remove(verificationToken.getToken());
    }
}
