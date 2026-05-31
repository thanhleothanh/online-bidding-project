package com.ghtk.onlinebiddingproject.adapter.out.repository.database.springdata;

import com.ghtk.onlinebiddingproject.adapter.out.repository.database.persistence.ReviewResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewResultJpaRepository extends JpaRepository<ReviewResultEntity, Integer> {

    Optional<ReviewResultEntity> findByAuctionId(Integer auctionId);
}
