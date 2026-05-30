package com.ghtk.onlinebiddingproject.adapter.out.websocket;

import com.ghtk.onlinebiddingproject.domain.port.WebSocketPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class WebSocketPublisherAdapter implements WebSocketPort {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketPublisherAdapter.class);

    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketPublisherAdapter(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void sendToUser(Integer userId, Object payload) {
        messagingTemplate.convertAndSendToUser(
                userId.toString(),
                "/queue/notifications",
                payload
        );
        logger.debug("Sent notification to user {}: {}", userId, payload);
    }

    @Override
    public void sendToAuctionBidders(Integer auctionId, Object payload) {
        messagingTemplate.convertAndSend(
                "/topic/auctions/" + auctionId + "/bids",
                payload
        );
        logger.debug("Sent bid update for auction {}: {}", auctionId, payload);
    }
}
