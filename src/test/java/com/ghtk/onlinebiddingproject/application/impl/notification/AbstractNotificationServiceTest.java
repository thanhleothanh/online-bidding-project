package com.ghtk.onlinebiddingproject.application.impl.notification;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ghtk.onlinebiddingproject.application.impl.NotificationServiceImpl;
import com.ghtk.onlinebiddingproject.domain.model.notification.NotificationRepository;
import com.ghtk.onlinebiddingproject.domain.model.notification.NotificationType;
import com.ghtk.onlinebiddingproject.domain.port.WebSocketPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public abstract class AbstractNotificationServiceTest {

    protected NotificationRepository notificationRepository;
    protected WebSocketPort webSocketPort;
    protected NotificationServiceImpl notificationService;

    @BeforeEach
    void setUp() {
        initDependencies();
        notificationService = new NotificationServiceImpl(notificationRepository, webSocketPort);
    }

    protected abstract void initDependencies();

    @Test
    void shouldCreateAuctionNotification() {
        var notification = notificationService.createAuctionNotification(1, NotificationType.SUBMIT_AUCTION, 2);

        assertNotNull(notification.getId());
        assertEquals(NotificationType.SUBMIT_AUCTION, notification.getType());
        assertEquals(1, notification.getAuctionId());
        assertTrue(notification.getRecipients().contains(2));
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
