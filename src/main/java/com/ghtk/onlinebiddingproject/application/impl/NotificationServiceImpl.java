package com.ghtk.onlinebiddingproject.application.impl;

import com.ghtk.onlinebiddingproject.application.NotificationService;
import com.ghtk.onlinebiddingproject.domain.model.notification.Notification;
import com.ghtk.onlinebiddingproject.domain.model.notification.NotificationRepository;
import com.ghtk.onlinebiddingproject.domain.model.notification.NotificationType;
import com.ghtk.onlinebiddingproject.domain.port.WebSocketPort;

import java.util.List;

public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final WebSocketPort webSocketPort;

    public NotificationServiceImpl(NotificationRepository notificationRepository, WebSocketPort webSocketPort) {
        this.notificationRepository = notificationRepository;
        this.webSocketPort = webSocketPort;
    }

    @Override
    public Notification createAuctionNotification(Integer auctionId, NotificationType type, Integer recipientId) {
        Notification notification = Notification.create(type, type.name() + " for auction " + auctionId);
        notification.linkToAuction(auctionId);
        notification.addRecipient(recipientId);
        Notification saved = notificationRepository.save(notification);
        webSocketPort.sendToUser(recipientId, saved);
        return saved;
    }

    @Override
    public Notification createReportNotification(Integer reportId, NotificationType type, Integer recipientId) {
        Notification notification = Notification.create(type, type.name() + " for report " + reportId);
        notification.linkToReport(reportId);
        notification.addRecipient(recipientId);
        Notification saved = notificationRepository.save(notification);
        webSocketPort.sendToUser(recipientId, saved);
        return saved;
    }

    @Override
    public List<Notification> getMyNotifications(Integer userId, int page, int size) {
        return notificationRepository.findByRecipientId(userId);
    }

    @Override
    public void deleteAuctionNotifications(Integer auctionId) {
        notificationRepository.deleteByAuctionId(auctionId);
    }

    @Override
    public void deleteReportNotifications(Integer reportId) {
        notificationRepository.deleteByReportId(reportId);
    }
}
