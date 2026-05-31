package com.ghtk.onlinebiddingproject.domain.model.notification;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Notification {

    private Integer id;
    private NotificationType type;
    private String entityType;
    private Integer senderProfileId;
    private final List<Integer> recipientIds;
    private final List<Integer> auctionIds;
    private final List<Integer> reportIds;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Notification(Integer id, NotificationType type, String entityType, Integer senderProfileId, LocalDateTime createdAt) {
        this.id = id;
        this.type = type;
        this.entityType = entityType;
        this.senderProfileId = senderProfileId;
        this.createdAt = createdAt;
        this.recipientIds = new ArrayList<>();
        this.auctionIds = new ArrayList<>();
        this.reportIds = new ArrayList<>();
    }

    public static Notification create(NotificationType type, String entityType, Integer senderProfileId) {
        return new Notification(null, type, entityType, senderProfileId, LocalDateTime.now());
    }

    public void addRecipient(Integer profileId) {
        recipientIds.add(profileId);
    }

    public void linkToAuction(Integer auctionId) {
        auctionIds.add(auctionId);
    }

    public void linkToReport(Integer reportId) {
        reportIds.add(reportId);
    }

    public List<Integer> getRecipients() {
        return List.copyOf(recipientIds);
    }

    public List<Integer> getAuctionIds() {
        return List.copyOf(auctionIds);
    }

    public List<Integer> getReportIds() {
        return List.copyOf(reportIds);
    }

    public boolean hasAuctionLinks() {
        return !auctionIds.isEmpty();
    }

    public boolean hasReportLinks() {
        return !reportIds.isEmpty();
    }
}
