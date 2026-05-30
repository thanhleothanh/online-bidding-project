package com.ghtk.onlinebiddingproject.domain.service;

import com.ghtk.onlinebiddingproject.domain.model.auth.Profile;
import com.ghtk.onlinebiddingproject.domain.model.auth.UserRepository;

public class LegitimateScoreService {

    private static final int DEDUCTION_AMOUNT = 5;

    private LegitimateScoreService() {}

    public static void deductScore(Integer profileId, UserRepository userRepository) {
        Profile profile = userRepository.findById(profileId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        profile.deductLegitimateScore(DEDUCTION_AMOUNT);
        userRepository.save(profile);
    }
}
