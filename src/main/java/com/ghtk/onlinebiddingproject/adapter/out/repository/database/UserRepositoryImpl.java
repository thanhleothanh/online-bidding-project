package com.ghtk.onlinebiddingproject.adapter.out.repository.database;

import com.ghtk.onlinebiddingproject.adapter.out.repository.database.mapper.AuthMapper;
import com.ghtk.onlinebiddingproject.adapter.out.repository.database.persistence.ProfileEntity;
import com.ghtk.onlinebiddingproject.adapter.out.repository.database.springdata.ProfileJpaRepository;
import com.ghtk.onlinebiddingproject.domain.model.auth.Profile;
import com.ghtk.onlinebiddingproject.domain.model.auth.UserRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final ProfileJpaRepository profileJpaRepository;

    public UserRepositoryImpl(ProfileJpaRepository profileJpaRepository) {
        this.profileJpaRepository = profileJpaRepository;
    }

    @Override
    public Optional<Profile> findById(Integer id) {
        return profileJpaRepository.findById(id).map(AuthMapper::toDomain);
    }

    @Override
    public Optional<Profile> findByUsername(String username) {
        return profileJpaRepository.findByUsername(username).map(AuthMapper::toDomain);
    }

    @Override
    @Transactional
    public Profile save(Profile profile) {
        ProfileEntity entity = AuthMapper.toEntity(profile);
        ProfileEntity saved = profileJpaRepository.save(entity);
        return AuthMapper.toDomain(saved);
    }

    @Override
    public boolean existsByUsername(String username) {
        return profileJpaRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return profileJpaRepository.existsByEmail(email);
    }

    @Override
    @Transactional
    public void insertUser(Integer profileId) {
        profileJpaRepository.insertUser(profileId);
    }

    @Override
    @Transactional
    public void insertAdmin(Integer profileId) {
        profileJpaRepository.insertAdmin(profileId);
    }
}
