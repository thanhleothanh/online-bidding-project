package com.ghtk.onlinebiddingproject.domain.model.notification;

public enum NotificationType {
    SUBMIT_AUCTION,
    REVIEW_AUCTION,
    START_AUCTION,
    END_AUCTION,
    NEW_BID_AUCTION,
    CREATE_REPORT,
    JUDGE_REPORT;

    public boolean isAuctionRelated() {
        return this == SUBMIT_AUCTION || this == REVIEW_AUCTION ||
               this == START_AUCTION || this == END_AUCTION || this == NEW_BID_AUCTION;
    }

    public boolean isReportRelated() {
        return this == CREATE_REPORT || this == JUDGE_REPORT;
    }
}
