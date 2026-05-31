package com.ghtk.onlinebiddingproject.adapter.out.repository.database.springdata;

import com.ghtk.onlinebiddingproject.adapter.out.repository.database.persistence.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationJpaRepository extends JpaRepository<NotificationEntity, Integer> {

    @Query("SELECT DISTINCT n FROM NotificationEntity n LEFT JOIN FETCH n.auctionLinks al LEFT JOIN FETCH n.reportLinks rl WHERE al.auction.id = :auctionId")
    List<NotificationEntity> findByAuctionId(@Param("auctionId") Integer auctionId);

    @Query("SELECT DISTINCT n FROM NotificationEntity n LEFT JOIN FETCH n.auctionLinks al LEFT JOIN FETCH n.reportLinks rl WHERE rl.report.id = :reportId")
    List<NotificationEntity> findByReportId(@Param("reportId") Integer reportId);
}
