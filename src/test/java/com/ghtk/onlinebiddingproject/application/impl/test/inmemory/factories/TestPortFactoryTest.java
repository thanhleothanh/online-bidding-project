package com.ghtk.onlinebiddingproject.application.impl.test.inmemory.factories;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import com.ghtk.onlinebiddingproject.application.impl.test.inmemory.InMemoryEventPublisher;
import com.ghtk.onlinebiddingproject.application.impl.test.inmemory.InMemoryImageStoragePort;
import com.ghtk.onlinebiddingproject.application.impl.test.inmemory.InMemoryJwtPort;
import com.ghtk.onlinebiddingproject.application.impl.test.inmemory.InMemoryMailPort;
import com.ghtk.onlinebiddingproject.application.impl.test.inmemory.InMemoryWebSocketPort;
import com.ghtk.onlinebiddingproject.application.port.JwtPort;
import com.ghtk.onlinebiddingproject.application.port.MailPort;
import com.ghtk.onlinebiddingproject.domain.event.EventPublisher;
import com.ghtk.onlinebiddingproject.domain.model.item.ImageStoragePort;
import com.ghtk.onlinebiddingproject.domain.port.WebSocketPort;
import org.junit.jupiter.api.Test;

class TestPortFactoryTest {

    @Test
    void shouldProvideAllPortImplementations() {
        EventPublisher eventPublisher = TestPortFactory.createEventPublisher();
        JwtPort jwtPort = TestPortFactory.createJwtPort();
        MailPort mailPort = TestPortFactory.createMailPort();
        WebSocketPort webSocketPort = TestPortFactory.createWebSocketPort();
        ImageStoragePort imageStoragePort = TestPortFactory.createImageStoragePort();

        assertNotNull(eventPublisher);
        assertNotNull(jwtPort);
        assertNotNull(mailPort);
        assertNotNull(webSocketPort);
        assertNotNull(imageStoragePort);

        assertInstanceOf(InMemoryEventPublisher.class, eventPublisher);
        assertInstanceOf(InMemoryJwtPort.class, jwtPort);
        assertInstanceOf(InMemoryMailPort.class, mailPort);
        assertInstanceOf(InMemoryWebSocketPort.class, webSocketPort);
        assertInstanceOf(InMemoryImageStoragePort.class, imageStoragePort);
    }

    @Test
    void shouldReturnFreshInstances() {
        EventPublisher pub1 = TestPortFactory.createEventPublisher();
        EventPublisher pub2 = TestPortFactory.createEventPublisher();
        assertNotSame(pub1, pub2);
    }
}
