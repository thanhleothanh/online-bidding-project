package com.ghtk.onlinebiddingproject.models.responses;

import com.ghtk.onlinebiddingproject.models.entities.Notification;

import java.util.List;

public record NotificationPagingResponse(Integer count, Integer page, Integer pageSize, Integer pageTotal, List<Notification> notifications) {
}
