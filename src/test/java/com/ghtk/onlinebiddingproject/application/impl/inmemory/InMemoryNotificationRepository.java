package com.ghtk.onlinebiddingproject.application.impl.inmemory;

import com.ghtk.onlinebiddingproject.domain.model.notification.Notification;
import com.ghtk.onlinebiddingproject.domain.model.notification.NotificationRepository;

import java.util.ArrayList;
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
            Notification newNotif = new Notification(id, notification.getType(), notification.getMessage(), notification.getCreatedAt());
            if (notification.getAuctionId() != null) newNotif.linkToAuction(notification.getAuctionId());
            if (notification.getReportId() != null) newNotif.linkToReport(notification.getReportId());
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
                .filter(n -> auctionId.equals(n.getAuctionId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Notification> findByReportId(Integer reportId) {
        return notifications.values().stream()
                .filter(n -> reportId.equals(n.getReportId()))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteByAuctionId(Integer auctionId) {
        notifications.values().removeIf(n -> auctionId.equals(n.getAuctionId()));
    }

    @Override
    public void deleteByReportId(Integer reportId) {
        notifications.values().removeIf(n -> reportId.equals(n.getReportId()));
    }
}
