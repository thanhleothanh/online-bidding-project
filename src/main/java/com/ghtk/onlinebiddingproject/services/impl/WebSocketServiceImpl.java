package com.ghtk.onlinebiddingproject.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghtk.onlinebiddingproject.models.dtos.BidDto;
import com.ghtk.onlinebiddingproject.models.entities.Notification;
import com.ghtk.onlinebiddingproject.models.entities.Profile;
import com.ghtk.onlinebiddingproject.services.WebSocketService;
import com.ghtk.onlinebiddingproject.utils.converters.EntityToDtoConverter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketServiceImpl implements WebSocketService {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private EntityToDtoConverter entityToDtoConverter;

    @Override
    @SneakyThrows
    public void sendBid(Integer auctionId, BidDto bidDto) {
        String dest = "/topic/auctions/" + auctionId + "/bids";
        String json = (new ObjectMapper().writeValueAsString(bidDto));
        simpMessagingTemplate.convertAndSend(dest, json);
    }

    //notifying users that they have new notifications
    @Override
    @SneakyThrows
    public void sendNotification(Profile profile, Notification notification) {
        boolean notifying = true;
        if (notification.getProfile() != null)
            notifying = !profile.getId().equals(notification.getProfile().getId());
        String username = profile.getUsername();
        String dest = "/queue/notifications";
        simpMessagingTemplate.convertAndSendToUser(username, dest, notifying);
    }
}
