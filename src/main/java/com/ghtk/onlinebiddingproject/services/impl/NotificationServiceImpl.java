package com.ghtk.onlinebiddingproject.services.impl;

import com.ghtk.onlinebiddingproject.constants.NotificationTypeConstants;
import com.ghtk.onlinebiddingproject.models.entities.*;
import com.ghtk.onlinebiddingproject.models.responses.NotificationPagingResponse;
import com.ghtk.onlinebiddingproject.repositories.AuctionUserRepository;
import com.ghtk.onlinebiddingproject.repositories.NotificationNotifiedRepository;
import com.ghtk.onlinebiddingproject.repositories.NotificationRepository;
import com.ghtk.onlinebiddingproject.repositories.ProfileRepository;
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
    private NotificationNotifiedRepository notificationNotifiedRepository;

    // get 10 latest notifications by userId
    @Override
    public NotificationPagingResponse getMyNotifications(HttpHeaders headers) {
        UserDetailsImpl userDetails = CurrentUserUtils.getCurrentUserDetails();
        Page<Notification> page;
        if (PaginationUtils.isPaginationRequested(headers))
            page = notificationRepository.findByNotificationNotifieds_Profile_Id(userDetails.getId(), PaginationUtils.buildPageRequest(headers, Sort.by(Sort.Direction.DESC, "updatedAt")));
        else
            page = notificationRepository.findByNotificationNotifieds_Profile_Id(userDetails.getId(), PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "updatedAt")));
        return new NotificationPagingResponse(
                (int) page.getTotalElements(),
                page.getNumber(),
                page.getNumberOfElements(),
                page.getTotalPages(),
                page.getContent());
    }

    @Override
    @Transactional
    public void createSubmitAuctionNotification(Auction auction) {
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
    }

    // create notification record when admin review the submitted auction
    @Override
    @Transactional
    public void createReviewAuctionNotification(Profile profile, Auction auction) {
        UserDetailsImpl userDetails = CurrentUserUtils.getCurrentUserDetails();
        Notification savedReviewAuctionNotification = notificationRepository.save(new Notification(
                new Profile(userDetails.getId()),
                NotificationTypeConstants.REVIEW_AUCTION.getNotificationType(),
                NotificationTypeConstants.REVIEW_AUCTION.getEntityType()
        ));
        notificationRepository.insertAuctionNotification(auction.getId(), savedReviewAuctionNotification.getId());
        NotificationNotified newNotificationNotified =
                new NotificationNotified(savedReviewAuctionNotification, new Profile(auction.getUser().getId()));
        notificationNotifiedRepository.save(newNotificationNotified);
    }

    // create notification record to inform users that auction has ended
    @Override
    @Transactional
    public void createEndAuctionNotification(Auction auction) {
        Notification savedEndAuctionNotification = notificationRepository.save(new Notification(
                null,
                NotificationTypeConstants.END_AUCTION.getNotificationType(),
                NotificationTypeConstants.END_AUCTION.getEntityType()
        ));
        notificationRepository.insertAuctionNotification(auction.getId(), savedEndAuctionNotification.getId());
        NotificationNotified newNotificationNotified =
                new NotificationNotified(savedEndAuctionNotification, new Profile(auction.getUser().getId()));
        notificationNotifiedRepository.save(newNotificationNotified);

        List<AuctionUser> auctionUsers = auctionUserRepository.findByAuction_Id(auction.getId());
        List<Profile> interestedProfiles = auctionUsers.stream().map(AuctionUser::getUser).map(User::getProfile).collect(Collectors.toList());

        interestedProfiles.forEach(profile -> {
            notificationNotifiedRepository.save(new NotificationNotified(savedEndAuctionNotification, profile));
        });
    }

    // create notification record to inform auction creator that auction has started
    @Override
    @Transactional
    public void createStartAuctionNotification(Auction auction) {
        Notification savedStartAuctionNotification = notificationRepository.save(new Notification(
                null,
                NotificationTypeConstants.START_AUCTION.getNotificationType(),
                NotificationTypeConstants.START_AUCTION.getEntityType()
        ));
        notificationRepository.insertAuctionNotification(auction.getId(), savedStartAuctionNotification.getId());
        NotificationNotified newNotificationNotified =
                new NotificationNotified(savedStartAuctionNotification, new Profile(auction.getUser().getId()));
        notificationNotifiedRepository.save(newNotificationNotified);
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
    public void saveNewBidAuctionNotification(Profile profile, Auction auction) {
        Notification savedNewBidAuctionNotification = notificationRepository.findByNotificationAuction_Auction_IdAndNotificationType(
                auction.getId(),
                NotificationTypeConstants.NEW_BID_AUCTION.getNotificationType()
        );
        savedNewBidAuctionNotification.setProfile(profile);
        savedNewBidAuctionNotification.setUpdatedAt(LocalDateTime.now());
        notificationRepository.save(savedNewBidAuctionNotification);
    }

    // add notification notified record when new person starts bidding
    @Override
    @Transactional
    public void createNewAuctionUserNotification(Profile profile, Auction auction) {
        Notification savedNewBidAuctionNotification = notificationRepository.findByNotificationAuction_Auction_IdAndNotificationType(
                auction.getId(),
                NotificationTypeConstants.NEW_BID_AUCTION.getNotificationType()
        );
        NotificationNotified newNotificationNotified =
                new NotificationNotified(savedNewBidAuctionNotification, profile);
        notificationNotifiedRepository.save(newNotificationNotified);
    }

    /*
     * Report notifications related
     * */

    @Override
    @Transactional
    public void createNewReportNotification(Report report) {
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
    }

    @Override
    @Transactional
    public void createJudgeReportNotification(Report report) {
        UserDetailsImpl userDetails = CurrentUserUtils.getCurrentUserDetails();
        Notification savedJudgeReportNotification = notificationRepository.save(new Notification(
                new Profile(userDetails.getId()),
                NotificationTypeConstants.JUDGE_REPORT.getNotificationType(),
                NotificationTypeConstants.JUDGE_REPORT.getEntityType()
        ));
        notificationRepository.insertReportNotification(report.getId(), savedJudgeReportNotification.getId());
        NotificationNotified newNotificationNotified =
                new NotificationNotified(savedJudgeReportNotification, new Profile(report.getUserReporter().getId()));
        notificationNotifiedRepository.save(newNotificationNotified);
    }

}
