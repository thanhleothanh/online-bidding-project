package com.ghtk.onlinebiddingproject.domain.event;

import com.ghtk.onlinebiddingproject.domain.model.shared.Money;

import java.time.Instant;

public record BidPlacedEvent(Integer auctionId, Integer bidId, Integer bidderId, Money amount) implements DomainEvent {
    @Override
    public Instant occurredOn() { return Instant.now(); }
    @Override
    public String eventName() { return "BID_PLACED"; }
}
