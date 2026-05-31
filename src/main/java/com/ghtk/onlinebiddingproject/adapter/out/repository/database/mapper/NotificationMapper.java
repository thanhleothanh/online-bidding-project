package com.ghtk.onlinebiddingproject.adapter.out.repository.database.mapper;

import com.ghtk.onlinebiddingproject.adapter.out.repository.database.persistence.*;
import com.ghtk.onlinebiddingproject.domain.model.notification.Notification;
import com.ghtk.onlinebiddingproject.domain.model.notification.NotificationType;
import jakarta.persistence.EntityManager;

import java.util.List;

public class NotificationMapper {

    private NotificationMapper() {
    }

    public static Notification toDomain(NotificationEntity entity) {
        if (entity == null) return null;
        Notification notification = new Notification(
                entity.getId(),
                NotificationType.valueOf(entity.getNotificationType()),
                entity.getEntityType(),
                entity.getProfile() != null ? entity.getProfile().getId() : null,
                entity.getCreatedAt()
        );
        notification.setUpdatedAt(entity.getUpdatedAt());

        if (entity.getAuctionLinks() != null) {
            for (NotificationAuctionEntity link : entity.getAuctionLinks()) {
                notification.linkToAuction(link.getAuction().getId());
            }
        }
        if (entity.getReportLinks() != null) {
            for (NotificationReportEntity link : entity.getReportLinks()) {
                notification.linkToReport(link.getReport().getId());
            }
        }
        return notification;
    }

    public static NotificationEntity toEntity(Notification domain, EntityManager em) {
        if (domain == null) return null;
        NotificationEntity entity = new NotificationEntity();
        entity.setId(domain.getId());
        entity.setNotificationType(domain.getType().name());
        entity.setEntityType(domain.getEntityType());
        if (domain.getSenderProfileId() != null) {
            entity.setProfile(em.getReference(ProfileEntity.class, domain.getSenderProfileId()));
        }
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());

        for (Integer auctionId : domain.getAuctionIds()) {
            NotificationAuctionEntity link = new NotificationAuctionEntity();
            link.setNotification(entity);
            link.setAuction(em.getReference(AuctionEntity.class, auctionId));
            entity.getAuctionLinks().add(link);
        }

        for (Integer reportId : domain.getReportIds()) {
            NotificationReportEntity link = new NotificationReportEntity();
            link.setNotification(entity);
            link.setReport(em.getReference(ReportEntity.class, reportId));
            entity.getReportLinks().add(link);
        }

        return entity;
    }

    public static List<Integer> extractAuctionIds(NotificationEntity entity) {
        if (entity == null || entity.getAuctionLinks() == null) return List.of();
        return entity.getAuctionLinks().stream()
                .map(link -> link.getAuction().getId())
                .toList();
    }

    public static List<Integer> extractReportIds(NotificationEntity entity) {
        if (entity == null || entity.getReportLinks() == null) return List.of();
        return entity.getReportLinks().stream()
                .map(link -> link.getReport().getId())
                .toList();
    }
}
