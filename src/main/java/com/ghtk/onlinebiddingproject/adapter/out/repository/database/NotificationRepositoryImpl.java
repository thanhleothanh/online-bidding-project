package com.ghtk.onlinebiddingproject.adapter.out.repository.database;

import com.ghtk.onlinebiddingproject.adapter.out.repository.database.mapper.NotificationMapper;
import com.ghtk.onlinebiddingproject.adapter.out.repository.database.persistence.NotificationEntity;
import com.ghtk.onlinebiddingproject.adapter.out.repository.database.persistence.NotificationNotifiedEntity;
import com.ghtk.onlinebiddingproject.adapter.out.repository.database.springdata.NotificationJpaRepository;
import com.ghtk.onlinebiddingproject.adapter.out.repository.database.springdata.NotificationNotifiedJpaRepository;
import com.ghtk.onlinebiddingproject.domain.model.notification.Notification;
import com.ghtk.onlinebiddingproject.domain.model.notification.NotificationRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class NotificationRepositoryImpl implements NotificationRepository {

    private final NotificationJpaRepository notificationJpaRepository;
    private final NotificationNotifiedJpaRepository notificationNotifiedJpaRepository;

    public NotificationRepositoryImpl(NotificationJpaRepository notificationJpaRepository,
                                      NotificationNotifiedJpaRepository notificationNotifiedJpaRepository) {
        this.notificationJpaRepository = notificationJpaRepository;
        this.notificationNotifiedJpaRepository = notificationNotifiedJpaRepository;
    }

    @Override
    public Optional<Notification> findById(Integer id) {
        return notificationJpaRepository.findById(id)
                .map(entity -> {
                    Notification notification = NotificationMapper.toDomain(entity);
                    loadRecipients(notification);
                    return notification;
                });
    }

    @Override
    @Transactional
    public Notification save(Notification notification) {
        NotificationEntity entity = NotificationMapper.toEntity(notification);
        NotificationEntity saved = notificationJpaRepository.save(entity);

        for (Integer recipientId : notification.getRecipients()) {
            NotificationNotifiedEntity notifiedEntity = new NotificationNotifiedEntity();
            notifiedEntity.setNotificationId(saved.getId());
            notifiedEntity.setProfileId(recipientId);
            notificationNotifiedJpaRepository.save(notifiedEntity);
        }

        return NotificationMapper.toDomain(saved);
    }

    @Override
    public List<Notification> findByRecipientId(Integer profileId) {
        List<NotificationNotifiedEntity> notifiedEntities = notificationNotifiedJpaRepository.findByProfileId(profileId);
        List<Notification> notifications = new ArrayList<>();

        for (NotificationNotifiedEntity notified : notifiedEntities) {
            notificationJpaRepository.findById(notified.getNotificationId())
                    .map(NotificationMapper::toDomain)
                    .ifPresent(notification -> {
                        loadRecipients(notification);
                        notifications.add(notification);
                    });
        }

        return notifications;
    }

    @Override
    public List<Notification> findByAuctionId(Integer auctionId) {
        return notificationJpaRepository.findByAuctionId(auctionId).stream()
                .map(entity -> {
                    Notification notification = NotificationMapper.toDomain(entity);
                    loadRecipients(notification);
                    return notification;
                })
                .toList();
    }

    @Override
    public List<Notification> findByReportId(Integer reportId) {
        return notificationJpaRepository.findByReportId(reportId).stream()
                .map(entity -> {
                    Notification notification = NotificationMapper.toDomain(entity);
                    loadRecipients(notification);
                    return notification;
                })
                .toList();
    }

    @Override
    @Transactional
    public void deleteByAuctionId(Integer auctionId) {
        List<NotificationEntity> notifications = notificationJpaRepository.findByAuctionId(auctionId);
        for (NotificationEntity notification : notifications) {
            notificationNotifiedJpaRepository.findByNotificationId(notification.getId())
                    .forEach(notificationNotifiedJpaRepository::delete);
            notificationJpaRepository.delete(notification);
        }
    }

    @Override
    @Transactional
    public void deleteByReportId(Integer reportId) {
        List<NotificationEntity> notifications = notificationJpaRepository.findByReportId(reportId);
        for (NotificationEntity notification : notifications) {
            notificationNotifiedJpaRepository.findByNotificationId(notification.getId())
                    .forEach(notificationNotifiedJpaRepository::delete);
            notificationJpaRepository.delete(notification);
        }
    }

    private void loadRecipients(Notification notification) {
        notificationNotifiedJpaRepository.findByNotificationId(notification.getId())
                .forEach(notified -> notification.addRecipient(notified.getProfileId()));
    }
}
