package com.ghtk.onlinebiddingproject.domain.event;

import java.time.Instant;

public interface DomainEvent {
    Instant occurredOn();
    String eventName();
}
