package com.ghtk.onlinebiddingproject.application.impl.test.inmemory;

import com.ghtk.onlinebiddingproject.domain.model.notification.Notification;
import com.ghtk.onlinebiddingproject.domain.model.notification.NotificationRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryNotificationRepository implements NotificationRepository {

    private final Map<Integer, Notification> notifications = new HashMap<>();
    private final AtomicInteger nextId = new AtomicInteger(1);

    @Override
    public Optional<Notification> findById(Integer id) {
        return Optional.ofNullable(notifications.get(id));
    }

    @Override
    public Notification save(Notification notification) {
        if (notification.getId() == null) {
            int id = nextId.getAndIncrement();
            Notification newNotif = new Notification(id, notification.getType(), notification.getEntityType(),
                    notification.getSenderProfileId(), notification.getCreatedAt());
            for (Integer auctionId : notification.getAuctionIds()) {
                newNotif.linkToAuction(auctionId);
            }
            for (Integer reportId : notification.getReportIds()) {
                newNotif.linkToReport(reportId);
            }
            for (Integer recipientId : notification.getRecipients()) {
                newNotif.addRecipient(recipientId);
            }
            notifications.put(id, newNotif);
            return newNotif;
        }
        notifications.put(notification.getId(), notification);
        return notification;
    }

    @Override
    public List<Notification> findByRecipientId(Integer profileId) {
        return notifications.values().stream()
                .filter(n -> n.getRecipients().contains(profileId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Notification> findByAuctionId(Integer auctionId) {
        return notifications.values().stream()
                .filter(n -> n.getAuctionIds().contains(auctionId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Notification> findByReportId(Integer reportId) {
        return notifications.values().stream()
                .filter(n -> n.getReportIds().contains(reportId))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteByAuctionId(Integer auctionId) {
        notifications.values().removeIf(n -> n.getAuctionIds().contains(auctionId));
    }

    @Override
    public void deleteByReportId(Integer reportId) {
        notifications.values().removeIf(n -> n.getReportIds().contains(reportId));
    }
}
