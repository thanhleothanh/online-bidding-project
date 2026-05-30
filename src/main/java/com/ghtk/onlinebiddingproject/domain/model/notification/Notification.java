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
    private String message;
    private LocalDateTime createdAt;
    private Integer auctionId;
    private Integer reportId;
    private final List<Integer> recipientIds;

    public Notification(Integer id, NotificationType type, String message, LocalDateTime createdAt) {
        this.id = id;
        this.type = type;
        this.message = message;
        this.createdAt = createdAt;
        this.recipientIds = new ArrayList<>();
    }

    public static Notification create(NotificationType type, String message) {
        return new Notification(null, type, message, LocalDateTime.now());
    }

    public void linkToAuction(Integer auctionId) {
        this.auctionId = auctionId;
    }

    public void linkToReport(Integer reportId) {
        this.reportId = reportId;
    }

    public void addRecipient(Integer profileId) {
        recipientIds.add(profileId);
    }

    public List<Integer> getRecipients() {
        return List.copyOf(recipientIds);
    }

    public boolean hasAuctionLink() {
        return auctionId != null;
    }

    public boolean hasReportLink() {
        return reportId != null;
    }

}
