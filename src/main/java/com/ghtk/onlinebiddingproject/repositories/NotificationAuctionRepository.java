package com.ghtk.onlinebiddingproject.repositories;

import com.ghtk.onlinebiddingproject.models.entities.NotificationAuction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationAuctionRepository extends JpaRepository<NotificationAuction, Integer> {

    List<NotificationAuction> findByAuction_Id(Integer id);

}
