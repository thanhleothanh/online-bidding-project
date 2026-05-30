package com.ghtk.onlinebiddingproject.application;

import com.ghtk.onlinebiddingproject.domain.model.notification.Notification;
import com.ghtk.onlinebiddingproject.domain.model.notification.NotificationType;

import java.util.List;

public interface NotificationService {
    Notification createAuctionNotification(Integer auctionId, NotificationType type, Integer recipientId);
    Notification createReportNotification(Integer reportId, NotificationType type, Integer recipientId);
    List<Notification> getMyNotifications(Integer userId, int page, int size);
    void deleteAuctionNotifications(Integer auctionId);
    void deleteReportNotifications(Integer reportId);
}
