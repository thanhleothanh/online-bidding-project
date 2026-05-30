package com.ghtk.onlinebiddingproject.adapter.out.repository.database.springdata;

import com.ghtk.onlinebiddingproject.adapter.out.repository.database.persistence.VerificationTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenJpaRepository extends JpaRepository<VerificationTokenEntity, Integer> {

    VerificationTokenEntity findByProfileId(Integer profileId);

    VerificationTokenEntity findByToken(String token);

    void deleteByProfileId(Integer profileId);
}
