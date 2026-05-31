package com.ghtk.onlinebiddingproject.infrastructure.quartz;

import com.ghtk.onlinebiddingproject.domain.model.auction.Auction;
import com.ghtk.onlinebiddingproject.domain.model.auction.AuctionRepository;
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
public class CancelAuctionJob extends QuartzJobBean {

    private final AuctionRepository auctionRepository;
    private final AuctionLifecycleService auctionLifecycleService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {
            JobDataMap jobDataMap = context.getMergedJobDataMap();
            Integer auctionId = jobDataMap.getInt("auctionId");
            
            Auction auction = auctionRepository.findById(auctionId)
                    .orElseThrow(() -> new RuntimeException("Auction not found with id: " + auctionId));
            
            // Only cancel if auction is still in a non-terminal state
            if (!auction.getStatus().isTerminal()) {
                auctionLifecycleService.cancelAuction(auctionId);
                log.info("Canceled auction with id {}", auctionId);
            } else {
                log.info("Auction {} was already in terminal state, skipping cancellation", auctionId);
            }
        } catch (Exception e) {
            log.error("Error executing CancelAuctionJob: {}", e.getMessage(), e);
        }
    }
}
