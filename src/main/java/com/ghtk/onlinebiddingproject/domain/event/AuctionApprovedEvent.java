package com.ghtk.onlinebiddingproject.domain.event;

import java.time.Instant;

public record AuctionApprovedEvent(Integer auctionId, Integer adminId) implements DomainEvent {
    @Override
    public Instant occurredOn() { return Instant.now(); }
    @Override
    public String eventName() { return "AUCTION_APPROVED"; }
}
