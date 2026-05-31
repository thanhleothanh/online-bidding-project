package com.ghtk.onlinebiddingproject.application.impl.notification;

import com.ghtk.onlinebiddingproject.domain.model.notification.NotificationRepository;
import com.ghtk.onlinebiddingproject.domain.port.WebSocketPort;
import com.ghtk.onlinebiddingproject.infrastructure.IntegrationDatabaseTest;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationDatabaseTest
class NotificationServiceImplDatabaseTest extends AbstractNotificationServiceTest {

    @Autowired
    private NotificationRepository autNotificationRepository;

    @Override
    protected void initDependencies() {
        notificationRepository = autNotificationRepository;
        webSocketPort = new NoOpWebSocketPort();
    }

    static class NoOpWebSocketPort implements WebSocketPort {
        @Override
        public void sendToUser(Integer userId, Object payload) {}

        @Override
        public void sendToAuctionBidders(Integer auctionId, Object payload) {}
    }
}
