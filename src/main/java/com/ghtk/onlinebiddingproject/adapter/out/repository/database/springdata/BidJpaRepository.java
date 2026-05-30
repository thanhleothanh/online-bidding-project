package com.ghtk.onlinebiddingproject.adapter.out.repository.database.springdata;

import com.ghtk.onlinebiddingproject.adapter.out.repository.database.persistence.BidEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BidJpaRepository extends JpaRepository<BidEntity, Integer> {

    List<BidEntity> findByAuctionId(Integer auctionId);

    Optional<BidEntity> findTopByAuctionIdOrderByPriceDesc(Integer auctionId);
}
