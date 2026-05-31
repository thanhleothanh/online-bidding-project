package com.ghtk.onlinebiddingproject.infrastructure.quartz;

import com.ghtk.onlinebiddingproject.application.NotificationService;
import com.ghtk.onlinebiddingproject.domain.model.auction.Auction;
import com.ghtk.onlinebiddingproject.domain.model.auction.AuctionRepository;
import com.ghtk.onlinebiddingproject.domain.model.notification.NotificationType;
import com.ghtk.onlinebiddingproject.domain.service.AuctionLifecycleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EndAuctionJob extends QuartzJobBean {

    private final AuctionRepository auctionRepository;
    private final AuctionLifecycleService auctionLifecycleService;
    private final NotificationService notificationService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {
            JobDataMap jobDataMap = context.getMergedJobDataMap();
            Integer auctionId = jobDataMap.getInt("auctionId");
            
            Auction auction = auctionRepository.findById(auctionId)
                    .orElseThrow(() -> new RuntimeException("Auction not found with id: " + auctionId));
            
            auctionLifecycleService.endAuction(auctionId);
            
            // Create end auction notification
            notificationService.createAuctionNotification(auctionId, NotificationType.END_AUCTION, auction.getOwnerId());
            
            log.info("Ended auction with id {}", auctionId);
        } catch (Exception e) {
            log.error("Error executing EndAuctionJob: {}", e.getMessage(), e);
        }
    }
}
