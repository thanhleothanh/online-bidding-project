package com.ghtk.onlinebiddingproject.adapter.out.repository.database.mapper;

import com.ghtk.onlinebiddingproject.adapter.out.repository.database.persistence.NotificationEntity;
import com.ghtk.onlinebiddingproject.adapter.out.repository.database.persistence.NotificationNotifiedEntity;
import com.ghtk.onlinebiddingproject.domain.model.notification.Notification;
import com.ghtk.onlinebiddingproject.domain.model.notification.NotificationType;

import java.util.ArrayList;
import java.util.List;

public class NotificationMapper {

    private NotificationMapper() {
    }

    public static Notification toDomain(NotificationEntity entity) {
        if (entity == null) return null;
        Notification notification = new Notification(
                entity.getId(),
                entity.getType(),
                entity.getMessage(),
                null
        );
        if (entity.getAuctionId() != null) {
            notification.linkToAuction(entity.getAuctionId());
        }
        if (entity.getReportId() != null) {
            notification.linkToReport(entity.getReportId());
        }
        return notification;
    }

    public static NotificationEntity toEntity(Notification domain) {
        if (domain == null) return null;
        NotificationEntity entity = new NotificationEntity();
        entity.setId(domain.getId());
        entity.setType(domain.getType());
        entity.setMessage(domain.getMessage());
        entity.setAuctionId(domain.getAuctionId());
        entity.setReportId(domain.getReportId());
        return entity;
    }


}
