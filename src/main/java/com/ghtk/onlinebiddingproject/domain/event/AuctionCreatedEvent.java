package com.ghtk.onlinebiddingproject.domain.event;

import java.time.Instant;

public record AuctionCreatedEvent(Integer auctionId, Integer ownerId) implements DomainEvent {
    @Override
    public Instant occurredOn() { return Instant.now(); }
    @Override
    public String eventName() { return "AUCTION_CREATED"; }
}
