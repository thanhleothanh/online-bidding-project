package com.ghtk.onlinebiddingproject.domain.model.notification;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotificationTest {

    @Test
    void shouldCreateNotification() {
        Notification n = Notification.create(NotificationType.SUBMIT_AUCTION, "New auction submitted");

        assertNull(n.getId());
        assertEquals(NotificationType.SUBMIT_AUCTION, n.getType());
        assertNotNull(n.getCreatedAt());
    }

    @Test
    void shouldLinkToAuction() {
        Notification n = Notification.create(NotificationType.SUBMIT_AUCTION, "message");
        n.linkToAuction(10);

        assertTrue(n.hasAuctionLink());
        assertEquals(10, n.getAuctionId());
    }

    @Test
    void shouldLinkToReport() {
        Notification n = Notification.create(NotificationType.CREATE_REPORT, "message");
        n.linkToReport(5);

        assertTrue(n.hasReportLink());
        assertEquals(5, n.getReportId());
    }

    @Test
    void shouldAddRecipient() {
        Notification n = Notification.create(NotificationType.SUBMIT_AUCTION, "message");
        n.addRecipient(1);
        n.addRecipient(2);

        assertEquals(2, n.getRecipients().size());
        assertTrue(n.getRecipients().contains(1));
        assertTrue(n.getRecipients().contains(2));
    }
}
