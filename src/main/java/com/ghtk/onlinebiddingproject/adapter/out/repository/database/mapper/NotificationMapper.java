package com.ghtk.onlinebiddingproject.adapter.out.repository.database.mapper;

import com.ghtk.onlinebiddingproject.adapter.out.repository.database.persistence.NotificationEntity;
import com.ghtk.onlinebiddingproject.adapter.out.repository.database.persistence.NotificationNotifiedEntity;
import com.ghtk.onlinebiddingproject.constants.NotificationTypeConstants;
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
                toDomainType(entity.getType()),
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
        entity.setType(toEntityType(domain.getType()));
        entity.setMessage(domain.getMessage());
        entity.setAuctionId(domain.getAuctionId());
        entity.setReportId(domain.getReportId());
        return entity;
    }

    public static NotificationType toDomainType(NotificationTypeConstants type) {
        if (type == null) return NotificationType.SUBMIT_AUCTION;
        return switch (type) {
            case SUBMIT_AUCTION -> NotificationType.SUBMIT_AUCTION;
            case REVIEW_AUCTION -> NotificationType.REVIEW_AUCTION;
            case START_AUCTION -> NotificationType.START_AUCTION;
            case END_AUCTION -> NotificationType.END_AUCTION;
            case NEW_BID_AUCTION -> NotificationType.NEW_BID_AUCTION;
            case CREATE_REPORT -> NotificationType.CREATE_REPORT;
            case JUDGE_REPORT -> NotificationType.JUDGE_REPORT;
        };
    }

    private static NotificationTypeConstants toEntityType(NotificationType type) {
        if (type == null) return NotificationTypeConstants.SUBMIT_AUCTION;
        return switch (type) {
            case SUBMIT_AUCTION -> NotificationTypeConstants.SUBMIT_AUCTION;
            case REVIEW_AUCTION -> NotificationTypeConstants.REVIEW_AUCTION;
            case START_AUCTION -> NotificationTypeConstants.START_AUCTION;
            case END_AUCTION -> NotificationTypeConstants.END_AUCTION;
            case NEW_BID_AUCTION -> NotificationTypeConstants.NEW_BID_AUCTION;
            case CREATE_REPORT -> NotificationTypeConstants.CREATE_REPORT;
            case JUDGE_REPORT -> NotificationTypeConstants.JUDGE_REPORT;
        };
    }
}
