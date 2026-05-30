package com.ghtk.onlinebiddingproject.adapter.out.repository.database.springdata;

import com.ghtk.onlinebiddingproject.adapter.out.repository.database.persistence.NotificationNotifiedEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationNotifiedJpaRepository extends JpaRepository<NotificationNotifiedEntity, Integer> {

    List<NotificationNotifiedEntity> findByNotificationId(Integer notificationId);

    List<NotificationNotifiedEntity> findByProfileId(Integer profileId);
}
