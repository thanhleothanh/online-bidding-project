package com.ghtk.onlinebiddingproject.application.impl;

import com.ghtk.onlinebiddingproject.application.impl.inmemory.InMemoryNotificationRepository;
import com.ghtk.onlinebiddingproject.application.impl.inmemory.InMemoryWebSocketPort;
import com.ghtk.onlinebiddingproject.domain.model.notification.NotificationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotificationServiceImplTest {

    private NotificationServiceImpl notificationService;
    private InMemoryNotificationRepository notificationRepository;
    private InMemoryWebSocketPort webSocketPort;

    @BeforeEach
    void setUp() {
        notificationRepository = new InMemoryNotificationRepository();
        webSocketPort = new InMemoryWebSocketPort();
        notificationService = new NotificationServiceImpl(notificationRepository, webSocketPort);
    }

    @Test
    void shouldCreateAuctionNotification() {
        var notification = notificationService.createAuctionNotification(1, NotificationType.SUBMIT_AUCTION, 2);

        assertNotNull(notification.getId());
        assertEquals(NotificationType.SUBMIT_AUCTION, notification.getType());
        assertEquals(1, notification.getAuctionId());
        assertTrue(notification.getRecipients().contains(2));
        assertEquals(1, webSocketPort.getSentToUser().size());
    }

    @Test
    void shouldCreateReportNotification() {
        var notification = notificationService.createReportNotification(10, NotificationType.CREATE_REPORT, 3);

        assertNotNull(notification.getId());
        assertEquals(NotificationType.CREATE_REPORT, notification.getType());
        assertEquals(10, notification.getReportId());
        assertTrue(notification.getRecipients().contains(3));
    }

    @Test
    void shouldGetMyNotifications() {
        notificationService.createAuctionNotification(1, NotificationType.SUBMIT_AUCTION, 2);
        notificationService.createAuctionNotification(2, NotificationType.START_AUCTION, 2);

        var notifications = notificationService.getMyNotifications(2, 0, 10);

        assertEquals(2, notifications.size());
    }

    @Test
    void shouldDeleteAuctionNotifications() {
        notificationService.createAuctionNotification(1, NotificationType.SUBMIT_AUCTION, 2);
        notificationService.createAuctionNotification(1, NotificationType.START_AUCTION, 3);

        notificationService.deleteAuctionNotifications(1);

        assertEquals(0, notificationRepository.findByAuctionId(1).size());
    }

    @Test
    void shouldDeleteReportNotifications() {
        notificationService.createReportNotification(10, NotificationType.CREATE_REPORT, 2);
        notificationService.createReportNotification(10, NotificationType.JUDGE_REPORT, 3);

        notificationService.deleteReportNotifications(10);

        assertEquals(0, notificationRepository.findByReportId(10).size());
    }
}
