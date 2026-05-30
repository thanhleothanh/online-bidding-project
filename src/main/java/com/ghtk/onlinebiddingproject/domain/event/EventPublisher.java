package com.ghtk.onlinebiddingproject.domain.event;

public interface EventPublisher {
    void publish(DomainEvent event);
}
