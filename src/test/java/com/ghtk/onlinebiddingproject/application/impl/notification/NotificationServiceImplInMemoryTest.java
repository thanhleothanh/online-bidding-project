package com.ghtk.onlinebiddingproject.application.impl.notification;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ghtk.onlinebiddingproject.application.impl.test.inmemory.InMemoryWebSocketPort;
import com.ghtk.onlinebiddingproject.application.impl.test.inmemory.factories.TestPortFactory;
import com.ghtk.onlinebiddingproject.application.impl.test.inmemory.factories.TestRepositoryFactory;
import com.ghtk.onlinebiddingproject.domain.model.notification.NotificationType;
import org.junit.jupiter.api.Test;

class NotificationServiceImplInMemoryTest extends AbstractNotificationServiceTest {

    @Override
    protected void initDependencies() {
        notificationRepository = TestRepositoryFactory.createNotificationRepository();
        webSocketPort = TestPortFactory.createWebSocketPort();
    }

    private InMemoryWebSocketPort sockets() {
        return (InMemoryWebSocketPort) webSocketPort;
    }

    @Test
    void shouldVerifyWebSocketSendToUser() {
        notificationService.createAuctionNotification(1, NotificationType.SUBMIT_AUCTION, 2);

        assertTrue(sockets().getSentToUser().containsKey(2));
        assertEquals(1, sockets().getSentToUser().get(2).size());
    }
}
