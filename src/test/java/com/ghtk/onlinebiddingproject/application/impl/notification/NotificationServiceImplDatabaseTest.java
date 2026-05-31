package com.ghtk.onlinebiddingproject.application.impl.notification;

import com.ghtk.onlinebiddingproject.domain.model.notification.NotificationRepository;
import com.ghtk.onlinebiddingproject.domain.port.WebSocketPort;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@ActiveProfiles("test")
@Tag("integration-tests")
@Sql(scripts = "classpath:sql/seed-notification-full.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
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
