package com.ghtk.onlinebiddingproject.adapter.out.repository.database.springdata;

import com.ghtk.onlinebiddingproject.adapter.out.repository.database.persistence.WinnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WinnerJpaRepository extends JpaRepository<WinnerEntity, Integer> {

    Optional<WinnerEntity> findByAuctionId(Integer auctionId);

    List<WinnerEntity> findByUserId(Integer userId);
}
