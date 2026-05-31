package com.ghtk.onlinebiddingproject.adapter.out.repository.database;

import com.ghtk.onlinebiddingproject.adapter.out.repository.database.mapper.NotificationMapper;
import com.ghtk.onlinebiddingproject.adapter.out.repository.database.persistence.NotificationEntity;
import com.ghtk.onlinebiddingproject.adapter.out.repository.database.persistence.NotificationNotifiedEntity;
import com.ghtk.onlinebiddingproject.adapter.out.repository.database.springdata.NotificationJpaRepository;
import com.ghtk.onlinebiddingproject.adapter.out.repository.database.springdata.NotificationNotifiedJpaRepository;
import com.ghtk.onlinebiddingproject.domain.model.notification.Notification;
import com.ghtk.onlinebiddingproject.domain.model.notification.NotificationRepository;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class NotificationRepositoryImpl implements NotificationRepository {

    private final NotificationJpaRepository notificationJpaRepository;
    private final NotificationNotifiedJpaRepository notificationNotifiedJpaRepository;
    private final EntityManager entityManager;

    public NotificationRepositoryImpl(NotificationJpaRepository notificationJpaRepository,
                                      NotificationNotifiedJpaRepository notificationNotifiedJpaRepository,
                                      EntityManager entityManager) {
        this.notificationJpaRepository = notificationJpaRepository;
        this.notificationNotifiedJpaRepository = notificationNotifiedJpaRepository;
        this.entityManager = entityManager;
    }

    @Override
    @Transactional(readOnly = true)
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
        NotificationEntity entity = NotificationMapper.toEntity(notification, entityManager);
        NotificationEntity saved = notificationJpaRepository.save(entity);

        for (Integer recipientId : notification.getRecipients()) {
            NotificationNotifiedEntity notifiedEntity = new NotificationNotifiedEntity();
            notifiedEntity.setNotificationId(saved.getId());
            notifiedEntity.setProfileId(recipientId);
            notificationNotifiedJpaRepository.save(notifiedEntity);
        }

        Notification result = NotificationMapper.toDomain(saved);
        loadRecipients(result);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Notification> findByRecipientId(Integer profileId) {
        List<NotificationNotifiedEntity> notifiedEntities = notificationNotifiedJpaRepository.findByProfileId(profileId);
        List<Notification> notifications = new ArrayList<>();

        for (NotificationNotifiedEntity notified : notifiedEntities) {
            NotificationEntity entity = entityManager.find(NotificationEntity.class, notified.getNotificationId());
            if (entity != null) {
                Notification notification = NotificationMapper.toDomain(entity);
                loadRecipients(notification);
                notifications.add(notification);
            }
        }

        return notifications;
    }

    @Override
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
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
