package com.ghtk.onlinebiddingproject.domain.port;

public interface WebSocketPort {
    void sendToUser(Integer userId, Object payload);
    void sendToAuctionBidders(Integer auctionId, Object payload);
}
