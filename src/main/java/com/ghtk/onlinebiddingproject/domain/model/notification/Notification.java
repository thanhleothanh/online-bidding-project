package com.ghtk.onlinebiddingproject.domain.model.notification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public Integer getId() { return id; }
    public NotificationType getType() { return type; }
    public String getMessage() { return message; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public Integer getAuctionId() { return auctionId; }
    public Integer getReportId() { return reportId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notification n = (Notification) o;
        return Objects.equals(id, n.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
