package com.ghtk.onlinebiddingproject.domain.model.notification;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository {
    Optional<Notification> findById(Integer id);
    Notification save(Notification notification);
    List<Notification> findByRecipientId(Integer profileId);
    List<Notification> findByAuctionId(Integer auctionId);
    List<Notification> findByReportId(Integer reportId);
    void deleteByAuctionId(Integer auctionId);
    void deleteByReportId(Integer reportId);
}
