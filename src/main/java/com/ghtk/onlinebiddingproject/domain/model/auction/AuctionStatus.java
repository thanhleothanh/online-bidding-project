package com.ghtk.onlinebiddingproject.domain.model.auction;

import java.util.Set;

public enum AuctionStatus {
    DRAFT,
    PENDING,
    QUEUED,
    OPENING,
    ENDED,
    CANCELED;

    private static final java.util.Map<AuctionStatus, Set<AuctionStatus>> TRANSITIONS = java.util.Map.of(
            DRAFT, Set.of(PENDING, CANCELED),
            PENDING, Set.of(QUEUED, CANCELED),
            QUEUED, Set.of(OPENING, CANCELED),
            OPENING, Set.of(ENDED, CANCELED),
            ENDED, Set.of(),
            CANCELED, Set.of()
    );

    public boolean canTransitionTo(AuctionStatus target) {
        return TRANSITIONS.getOrDefault(this, Set.of()).contains(target);
    }

    public boolean isTerminal() {
        return this == ENDED || this == CANCELED;
    }

    public boolean canBeBiddedOn() {
        return this == OPENING;
    }

    public boolean mustHaveBidsToEnd() {
        return this == OPENING;
    }
}
