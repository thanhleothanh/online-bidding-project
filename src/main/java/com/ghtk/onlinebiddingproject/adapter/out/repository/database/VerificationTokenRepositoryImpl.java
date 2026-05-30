package com.ghtk.onlinebiddingproject.adapter.out.repository.database;

import com.ghtk.onlinebiddingproject.adapter.out.repository.database.mapper.AuthMapper;
import com.ghtk.onlinebiddingproject.adapter.out.repository.database.persistence.VerificationTokenEntity;
import com.ghtk.onlinebiddingproject.adapter.out.repository.database.springdata.VerificationTokenJpaRepository;
import com.ghtk.onlinebiddingproject.domain.model.auth.VerificationToken;
import com.ghtk.onlinebiddingproject.domain.model.auth.VerificationTokenRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public class VerificationTokenRepositoryImpl implements VerificationTokenRepository {

    private final VerificationTokenJpaRepository verificationTokenJpaRepository;

    public VerificationTokenRepositoryImpl(VerificationTokenJpaRepository verificationTokenJpaRepository) {
        this.verificationTokenJpaRepository = verificationTokenJpaRepository;
    }

    @Override
    public Optional<VerificationToken> findById(Integer id) {
        return verificationTokenJpaRepository.findById(id).map(AuthMapper::toDomain);
    }

    @Override
    public VerificationToken findByProfileId(Integer profileId) {
        VerificationTokenEntity entity = verificationTokenJpaRepository.findByProfileId(profileId);
        return AuthMapper.toDomain(entity);
    }

    @Override
    public VerificationToken findByToken(String token) {
        VerificationTokenEntity entity = verificationTokenJpaRepository.findByToken(token);
        return AuthMapper.toDomain(entity);
    }

    @Override
    @Transactional
    public VerificationToken save(VerificationToken verificationToken) {
        VerificationTokenEntity entity = AuthMapper.toEntity(verificationToken);
        VerificationTokenEntity saved = verificationTokenJpaRepository.save(entity);
        return AuthMapper.toDomain(saved);
    }

    @Override
    @Transactional
    public void delete(VerificationToken verificationToken) {
        verificationTokenJpaRepository.deleteById(verificationToken.getId());
    }
}
