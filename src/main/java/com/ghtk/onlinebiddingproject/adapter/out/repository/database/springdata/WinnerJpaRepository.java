package com.ghtk.onlinebiddingproject.adapter.out.repository.database.springdata;

import com.ghtk.onlinebiddingproject.adapter.out.repository.database.persistence.WinnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WinnerJpaRepository extends JpaRepository<WinnerEntity, Integer> {

    Optional<WinnerEntity> findByAuctionId(Integer auctionId);

    @Query("SELECT w FROM WinnerEntity w JOIN w.bid b WHERE b.user.id = :userId")
    List<WinnerEntity> findByUserId(@Param("userId") Integer userId);
}
