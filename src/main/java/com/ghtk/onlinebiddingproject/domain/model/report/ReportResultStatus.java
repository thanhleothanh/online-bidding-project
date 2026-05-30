package com.ghtk.onlinebiddingproject.domain.model.report;

public enum ReportResultStatus {
    ACCEPTED,
    REJECTED;

    public boolean isAccepted() { return this == ACCEPTED; }
    public boolean isRejected() { return this == REJECTED; }
}
