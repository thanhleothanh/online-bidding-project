package com.ghtk.onlinebiddingproject.adapter.out.repository.database.springdata;

import com.ghtk.onlinebiddingproject.adapter.out.repository.database.persistence.AuctionEntity;
import com.ghtk.onlinebiddingproject.constants.AuctionStatusConstants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuctionJpaRepository extends JpaRepository<AuctionEntity, Integer>, JpaSpecificationExecutor<AuctionEntity> {

    List<AuctionEntity> findByOwnerId(Integer ownerId);

    List<AuctionEntity> findByOwnerIdAndStatus(Integer ownerId, AuctionStatusConstants status);

    List<AuctionEntity> findByStatus(AuctionStatusConstants status);
}
