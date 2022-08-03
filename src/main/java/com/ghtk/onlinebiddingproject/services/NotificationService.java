package com.ghtk.onlinebiddingproject.services;

import com.ghtk.onlinebiddingproject.models.entities.*;
import com.ghtk.onlinebiddingproject.models.responses.NotificationPagingResponse;
import org.springframework.http.HttpHeaders;

public interface NotificationService {
    NotificationPagingResponse getMyNotifications(HttpHeaders headers);

    Notification createSubmitAuctionNotification(Auction auction);

    Notification createReviewAuctionNotification(Auction auction);

    Notification createEndAuctionNotification(Auction auction);

    Notification createStartAuctionNotification(Auction auction);

    void createNewBidAuctionNotification(Auction auction);

    Notification saveNewBidAuctionNotification(Auction auction);

    void createNewAuctionUserNotification(Profile profile, Auction auction);

    Notification createNewReportNotification(Report report);

    Notification createJudgeReportNotification(Report report);

    //deleting notifications

    void deleteAuctionNotifications(Integer auctionId);

    void deleteReportNotifications(Integer reportId);

    //realtime notifying

    void notifyThroughSocket(Notification notification);
}
