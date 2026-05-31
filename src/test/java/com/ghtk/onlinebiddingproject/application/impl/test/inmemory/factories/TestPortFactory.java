package com.ghtk.onlinebiddingproject.application.impl.test.inmemory.factories;

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

public final class TestPortFactory {

    private TestPortFactory() {}

    public static EventPublisher createEventPublisher() {
        return new InMemoryEventPublisher();
    }

    public static JwtPort createJwtPort() {
        return new InMemoryJwtPort();
    }

    public static MailPort createMailPort() {
        return new InMemoryMailPort();
    }

    public static WebSocketPort createWebSocketPort() {
        return new InMemoryWebSocketPort();
    }

    public static ImageStoragePort createImageStoragePort() {
        return new InMemoryImageStoragePort();
    }
}
