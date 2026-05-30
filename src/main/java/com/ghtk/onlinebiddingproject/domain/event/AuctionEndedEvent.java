package com.ghtk.onlinebiddingproject.domain.event;

import java.time.Instant;

public record AuctionEndedEvent(Integer auctionId, Integer winnerId) implements DomainEvent {
    @Override
    public Instant occurredOn() { return Instant.now(); }
    @Override
    public String eventName() { return "AUCTION_ENDED"; }
}
