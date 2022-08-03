package com.ghtk.onlinebiddingproject.repositories;

import com.ghtk.onlinebiddingproject.models.entities.NotificationNotified;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationNotifiedRepository extends JpaRepository<NotificationNotified, Integer> {
    List<NotificationNotified> findByNotification_Id(Integer id);

}
