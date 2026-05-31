package com.ghtk.onlinebiddingproject.domain.model.review;

public enum ReviewResultStatus {
    ACCEPTED,
    REJECTED;

    public boolean isAccepted() { return this == ACCEPTED; }
    public boolean isRejected() { return this == REJECTED; }
}
