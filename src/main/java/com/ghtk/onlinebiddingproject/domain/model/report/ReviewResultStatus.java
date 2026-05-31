package com.ghtk.onlinebiddingproject.domain.model.report;

public enum ReviewResultStatus {
    ACCEPTED,
    REJECTED;

    public boolean isAccepted() { return this == ACCEPTED; }
    public boolean isRejected() { return this == REJECTED; }
}
