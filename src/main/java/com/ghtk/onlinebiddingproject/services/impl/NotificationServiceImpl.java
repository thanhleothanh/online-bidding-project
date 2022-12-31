package com.ghtk.onlinebiddingproject.services.impl;

import com.ghtk.onlinebiddingproject.constants.NotificationTypeConstants;
import com.ghtk.onlinebiddingproject.models.entities.*;
import com.ghtk.onlinebiddingproject.models.responses.NotificationPagingResponse;
import com.ghtk.onlinebiddingproject.repositories.*;
import com.ghtk.onlinebiddingproject.security.UserDetailsImpl;
import com.ghtk.onlinebiddingproject.services.NotificationService;
import com.ghtk.onlinebiddingproject.utils.CurrentUserUtils;
import com.ghtk.onlinebiddingproject.utils.PaginationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private AuctionUserRepository auctionUserRepository;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private NotificationAuctionRepository notificationAuctionRepository;
    @Autowired
    private NotificationReportRepository notificationReportRepository;
    @Autowired
    private NotificationNotifiedRepository notificationNotifiedRepository;
    @Autowired
    private WebSocketServiceImpl webSocketService;

    // get 10 latest notifications by userId
    @Override
    public NotificationPagingResponse getMyNotifications(HttpHeaders headers) {
        UserDetailsImpl userDetails = CurrentUserUtils.getCurrentUserDetails();
        Page<Notification> page;
        if (PaginationUtils.isPaginationRequested(headers)) {
            page = notificationRepository.findByNotificationNotifieds_Profile_Id(userDetails.getId(),
                    PaginationUtils.buildPageRequest(headers, Sort.by(Sort.Direction.DESC, "updatedAt")));
        } else {
            page = notificationRepository.findByNotificationNotifieds_Profile_Id(userDetails.getId(),
                    PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "updatedAt")));
        }
        return new NotificationPagingResponse(
                (int) page.getTotalElements(),
                page.getNumber(),
                page.getNumberOfElements(),
                page.getTotalPages(),
                page.getContent());
    }

    // create notification record when a user submit an auction
    @Override
    @Transactional
    public Notification createSubmitAuctionNotification(Auction auction) {
        UserDetailsImpl userDetails = CurrentUserUtils.getCurrentUserDetails();
        Notification savedSubmitAuctionNotification = notificationRepository.save(new Notification(
                new Profile(userDetails.getId()),
                NotificationTypeConstants.SUBMIT_AUCTION.getNotificationType(),
                NotificationTypeConstants.SUBMIT_AUCTION.getEntityType()
        ));
        notificationRepository.insertAuctionNotification(auction.getId(), savedSubmitAuctionNotification.getId());
        List<Profile> admins = profileRepository.findByRole_Id(1);
        admins.forEach(admin -> {
            notificationNotifiedRepository.save(new NotificationNotified(savedSubmitAuctionNotification, admin));
        });
        return savedSubmitAuctionNotification;
    }

    // create notification record when admin review the submitted auction
    @Override
    @Transactional
    public Notification createReviewAuctionNotification(Auction auction) {
        UserDetailsImpl userDetails = CurrentUserUtils.getCurrentUserDetails();
        Notification savedReviewAuctionNotification = notificationRepository.save(new Notification(
                new Profile(userDetails.getId()),
                NotificationTypeConstants.REVIEW_AUCTION.getNotificationType(),
                NotificationTypeConstants.REVIEW_AUCTION.getEntityType()
        ));
        notificationRepository.insertAuctionNotification(auction.getId(), savedReviewAuctionNotification.getId());
        NotificationNotified newNotificationNotified =
                new NotificationNotified(savedReviewAuctionNotification, auction.getUser().getProfile());
        notificationNotifiedRepository.save(newNotificationNotified);
        return savedReviewAuctionNotification;
    }

    // create notification record to inform users that auction has ended
    @Override
    @Transactional
    public Notification createEndAuctionNotification(Auction auction) {
        Notification savedEndAuctionNotification = notificationRepository.save(new Notification(
                null,
                NotificationTypeConstants.END_AUCTION.getNotificationType(),
                NotificationTypeConstants.END_AUCTION.getEntityType()
        ));
        notificationRepository.insertAuctionNotification(auction.getId(), savedEndAuctionNotification.getId());
        NotificationNotified newNotificationNotified =
                new NotificationNotified(savedEndAuctionNotification, auction.getUser().getProfile());
        notificationNotifiedRepository.save(newNotificationNotified);

        List<AuctionUser> auctionUsers = auctionUserRepository.findByAuction_Id(auction.getId());
        List<Profile> interestedProfiles = auctionUsers.stream().map(AuctionUser::getUser).map(User::getProfile).collect(Collectors.toList());
        interestedProfiles.forEach(profile -> {
            notificationNotifiedRepository.save(new NotificationNotified(savedEndAuctionNotification, profile));
        });
        return savedEndAuctionNotification;
    }

    // create notification record to inform auction creator that auction has started
    @Override
    @Transactional
    public Notification createStartAuctionNotification(Auction auction) {
        Notification savedStartAuctionNotification = notificationRepository.save(new Notification(
                null,
                NotificationTypeConstants.START_AUCTION.getNotificationType(),
                NotificationTypeConstants.START_AUCTION.getEntityType()
        ));
        notificationRepository.insertAuctionNotification(auction.getId(), savedStartAuctionNotification.getId());
        NotificationNotified newNotificationNotified =
                new NotificationNotified(savedStartAuctionNotification, auction.getUser().getProfile());
        notificationNotifiedRepository.save(newNotificationNotified);
        return savedStartAuctionNotification;
    }

    // create notification record as a placeholder that gets updated when new bids get added
    @Override
    @Transactional
    public void createNewBidAuctionNotification(Auction auction) {
        Notification savedNewBidAuctionNotification = notificationRepository.save(new Notification(
                null,
                NotificationTypeConstants.NEW_BID_AUCTION.getNotificationType(),
                NotificationTypeConstants.NEW_BID_AUCTION.getEntityType()
        ));
        notificationRepository.insertAuctionNotification(auction.getId(), savedNewBidAuctionNotification.getId());
    }

    // update the placeholder when new bids get added
    @Override
    @Transactional
    public Notification saveNewBidAuctionNotification(Auction auction) {
        UserDetailsImpl userDetails = CurrentUserUtils.getCurrentUserDetails();
        Notification savedNewBidAuctionNotification = notificationRepository.findByNotificationAuction_Auction_IdAndNotificationType(
                auction.getId(),
                NotificationTypeConstants.NEW_BID_AUCTION.getNotificationType()
        );
        savedNewBidAuctionNotification.setProfile(new Profile(userDetails.getId()));
        savedNewBidAuctionNotification.setUpdatedAt(LocalDateTime.now());
        notificationRepository.save(savedNewBidAuctionNotification);
        return savedNewBidAuctionNotification;
    }

    // add notification notified record when new person starts bidding
    @Override
    @Transactional
    public void createNewAuctionUserNotification(Profile profile, Auction auction) {
        Notification savedNewAuctionUserNotification = notificationRepository.findByNotificationAuction_Auction_IdAndNotificationType(
                auction.getId(),
                NotificationTypeConstants.NEW_BID_AUCTION.getNotificationType()
        );
        NotificationNotified newNotificationNotified =
                new NotificationNotified(savedNewAuctionUserNotification, profile);
        notificationNotifiedRepository.save(newNotificationNotified);
    }

    /*
     * Report notifications related
     * */

    @Override
    @Transactional
    public Notification createNewReportNotification(Report report) {
        UserDetailsImpl userDetails = CurrentUserUtils.getCurrentUserDetails();
        Notification savedNewReportNotification = notificationRepository.save(new Notification(
                new Profile(userDetails.getId()),
                NotificationTypeConstants.CREATE_REPORT.getNotificationType(),
                NotificationTypeConstants.CREATE_REPORT.getEntityType()
        ));
        notificationRepository.insertReportNotification(report.getId(), savedNewReportNotification.getId());

        List<Profile> admins = profileRepository.findByRole_Id(1);
        admins.forEach(admin -> {
            notificationNotifiedRepository.save(new NotificationNotified(savedNewReportNotification, admin));
        });
        return savedNewReportNotification;
    }

    @Override
    @Transactional
    public Notification createJudgeReportNotification(Report report) {
        UserDetailsImpl userDetails = CurrentUserUtils.getCurrentUserDetails();
        Notification savedJudgeReportNotification = notificationRepository.save(new Notification(
                new Profile(userDetails.getId()),
                NotificationTypeConstants.JUDGE_REPORT.getNotificationType(),
                NotificationTypeConstants.JUDGE_REPORT.getEntityType()
        ));
        notificationRepository.insertReportNotification(report.getId(), savedJudgeReportNotification.getId());
        NotificationNotified newNotificationNotified =
                new NotificationNotified(savedJudgeReportNotification, report.getUserReporter().getProfile());
        notificationNotifiedRepository.save(newNotificationNotified);
        return savedJudgeReportNotification;
    }

    //deleting notifications

    @Override
    @Transactional
    public void deleteAuctionNotifications(Integer auctionId) {
        List<NotificationAuction> notificationAuctions = notificationAuctionRepository.findByAuction_Id(auctionId);
        notificationAuctions.forEach(notificationAuction -> {
            notificationRepository.deleteById(notificationAuction.getNotification().getId());
        });
    }

    @Override
    @Transactional
    public void deleteReportNotifications(Integer reportId) {
        List<NotificationReport> notificationReports = notificationReportRepository.findByReport_Id(reportId);
        notificationReports.forEach(notificationReport -> {
            notificationRepository.deleteById(notificationReport.getNotification().getId());
        });
    }

    //realtime notifying

    @Override
    @Transactional
    public void notifyThroughSocket(Notification notification) {
        List<NotificationNotified> notifiedProfiles = notificationNotifiedRepository.findByNotification_Id(notification.getId());
        notifiedProfiles.forEach(profile -> {
            if (profile.getProfile() != null && profile.getProfile().getUsername() != null) {
                webSocketService.sendNotification(profile.getProfile(), notification);
            }
        });
    }
}
