package com.ghtk.onlinebiddingproject.application.impl.test.inmemory;

import com.ghtk.onlinebiddingproject.domain.port.WebSocketPort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryWebSocketPort implements WebSocketPort {

    private final Map<Integer, List<Object>> sentToUser = new HashMap<>();
    private final Map<Integer, List<Object>> sentToAuction = new HashMap<>();

    @Override
    public void sendToUser(Integer userId, Object payload) {
        sentToUser.computeIfAbsent(userId, k -> new ArrayList<>()).add(payload);
    }

    @Override
    public void sendToAuctionBidders(Integer auctionId, Object payload) {
        sentToAuction.computeIfAbsent(auctionId, k -> new ArrayList<>()).add(payload);
    }

    public Map<Integer, List<Object>> getSentToUser() { return sentToUser; }
    public Map<Integer, List<Object>> getSentToAuction() { return sentToAuction; }
}
