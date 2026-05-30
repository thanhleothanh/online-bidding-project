package com.ghtk.onlinebiddingproject.domain.model.notification;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotificationTypeTest {

    @Test
    void shouldIdentifyAuctionRelated() {
        assertTrue(NotificationType.SUBMIT_AUCTION.isAuctionRelated());
        assertTrue(NotificationType.REVIEW_AUCTION.isAuctionRelated());
        assertTrue(NotificationType.START_AUCTION.isAuctionRelated());
        assertTrue(NotificationType.END_AUCTION.isAuctionRelated());
        assertTrue(NotificationType.NEW_BID_AUCTION.isAuctionRelated());
        assertFalse(NotificationType.CREATE_REPORT.isAuctionRelated());
        assertFalse(NotificationType.JUDGE_REPORT.isAuctionRelated());
    }

    @Test
    void shouldIdentifyReportRelated() {
        assertTrue(NotificationType.CREATE_REPORT.isReportRelated());
        assertTrue(NotificationType.JUDGE_REPORT.isReportRelated());
        assertFalse(NotificationType.SUBMIT_AUCTION.isReportRelated());
    }
}
