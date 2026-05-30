package com.ghtk.onlinebiddingproject.jobs;

import com.ghtk.onlinebiddingproject.exceptions.NotFoundException;
import com.ghtk.onlinebiddingproject.models.entities.Auction;
import com.ghtk.onlinebiddingproject.models.entities.Notification;
import com.ghtk.onlinebiddingproject.repositories.AuctionRepository;
import com.ghtk.onlinebiddingproject.services.impl.NotificationServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.quartz.JobDataMap;
import lombok.RequiredArgsConstructor;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StartAuctionJob extends QuartzJobBean {
    private final AuctionRepository auctionRepository;
    private final NotificationServiceImpl notificationService;

    @Override
    protected void executeInternal(@NotNull JobExecutionContext context) throws JobExecutionException {
        try {
            JobDataMap jobDataMap = context.getMergedJobDataMap();
            Integer auctionId = jobDataMap.getInt("auctionId");
            Auction auction = auctionRepository.findById(auctionId)
                    .orElseThrow(() -> new NotFoundException("Không tìm thấy auction với id này!"));
            auctionRepository.startAuction(auctionId);

            Notification notification = notificationService.createStartAuctionNotification(auction);
            notificationService.createNewBidAuctionNotification(auction);
            notificationService.createNewAuctionUserNotification(auction.getUser().getProfile(), auction);
            notificationService.notifyThroughSocket(notification);
            log.info("started auction with id " + auctionId);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
    }
}
