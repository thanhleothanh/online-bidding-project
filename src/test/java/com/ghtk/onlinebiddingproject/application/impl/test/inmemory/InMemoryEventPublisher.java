package com.ghtk.onlinebiddingproject.application.impl.test.inmemory;

import com.ghtk.onlinebiddingproject.domain.event.DomainEvent;
import com.ghtk.onlinebiddingproject.domain.event.EventPublisher;

import java.util.ArrayList;
import java.util.List;

public class InMemoryEventPublisher implements EventPublisher {

    private final List<DomainEvent> publishedEvents = new ArrayList<>();

    @Override
    public void publish(DomainEvent event) {
        publishedEvents.add(event);
    }

    public List<DomainEvent> getPublishedEvents() {
        return publishedEvents;
    }

    public void clear() {
        publishedEvents.clear();
    }
}
