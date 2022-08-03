package com.ghtk.onlinebiddingproject.services;

import com.ghtk.onlinebiddingproject.models.dtos.BidDto;
import com.ghtk.onlinebiddingproject.models.entities.Notification;
import com.ghtk.onlinebiddingproject.models.entities.Profile;

public interface WebSocketService {

    void sendBid(Integer auctionId, BidDto bidDto);

    void sendNotification(Profile profile, Notification notification);
}
