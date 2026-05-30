package com.ghtk.onlinebiddingproject.adapter.out.repository.database.mapper;

import com.ghtk.onlinebiddingproject.adapter.out.repository.database.persistence.ProfileEntity;
import com.ghtk.onlinebiddingproject.adapter.out.repository.database.persistence.RoleEntity;
import com.ghtk.onlinebiddingproject.adapter.out.repository.database.persistence.VerificationTokenEntity;
import com.ghtk.onlinebiddingproject.domain.model.auth.Profile;
import com.ghtk.onlinebiddingproject.domain.model.auth.Role;
import com.ghtk.onlinebiddingproject.domain.model.auth.UserStatus;
import com.ghtk.onlinebiddingproject.domain.model.auth.VerificationToken;

public class AuthMapper {

    private AuthMapper() {
    }

    public static Profile toDomain(ProfileEntity entity) {
        if (entity == null) return null;
        Profile profile = new Profile(
                entity.getId(),
                entity.getUsername(),
                entity.getPassword(),
                entity.getName(),
                entity.getEmail(),
                toDomain(entity.getRole())
        );
        profile.setStatus(entity.getStatus());
        profile.setBio(entity.getBio());
        profile.setImageUrl(entity.getImageUrl());
        profile.setLegitimateScore(entity.getLegitimateScore() != null ? entity.getLegitimateScore() : 0);
        return profile;
    }

    public static ProfileEntity toEntity(Profile domain) {
        if (domain == null) return null;
        ProfileEntity entity = new ProfileEntity();
        entity.setId(domain.getId());
        entity.setUsername(domain.getUsername());
        entity.setPassword(domain.getPassword());
        entity.setName(domain.getName());
        entity.setEmail(domain.getEmail());
        entity.setBio(domain.getBio());
        entity.setImageUrl(domain.getImageUrl());
        entity.setLegitimateScore(domain.getLegitimateScore());
        entity.setStatus(domain.getStatus());
        entity.setRole(toEntity(domain.getRole()));
        return entity;
    }

    public static Role toDomain(RoleEntity entity) {
        if (entity == null) return null;
        return Role.of(entity.getId(), entity.getName());
    }

    public static RoleEntity toEntity(Role domain) {
        if (domain == null) return null;
        RoleEntity entity = new RoleEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        return entity;
    }

    public static VerificationToken toDomain(VerificationTokenEntity entity) {
        if (entity == null) return null;
        return new VerificationToken(
                entity.getId(),
                entity.getProfileId(),
                entity.getToken(),
                entity.getExpirationTime()
        );
    }

    public static VerificationTokenEntity toEntity(VerificationToken domain) {
        if (domain == null) return null;
        VerificationTokenEntity entity = new VerificationTokenEntity();
        entity.setId(domain.getId());
        entity.setToken(domain.getToken());
        entity.setExpirationTime(domain.getExpirationTime());
        entity.setProfileId(domain.getProfileId());
        return entity;
    }


}
