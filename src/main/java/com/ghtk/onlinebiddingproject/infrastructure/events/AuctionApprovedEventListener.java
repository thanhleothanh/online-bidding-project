package com.ghtk.onlinebiddingproject.infrastructure.events;

import com.ghtk.onlinebiddingproject.application.JobSchedulerService;
import com.ghtk.onlinebiddingproject.domain.event.AuctionApprovedEvent;
import com.ghtk.onlinebiddingproject.domain.model.auction.Auction;
import com.ghtk.onlinebiddingproject.domain.model.auction.AuctionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuctionApprovedEventListener {

    private final JobSchedulerService jobSchedulerService;
    private final AuctionRepository auctionRepository;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleAuctionApproved(AuctionApprovedEvent event) {
        try {
            Auction auction = auctionRepository.findById(event.auctionId())
                    .orElseThrow(() -> new RuntimeException("Auction not found: " + event.auctionId()));
            
            // Schedule start and end jobs
            jobSchedulerService.startAuctionScheduler(auction);
            jobSchedulerService.endAuctionScheduler(auction);
            
            log.info("Scheduled start job for auction {} at {} and end job at {}", 
                    auction.getId(), auction.getTimeStart(), auction.getTimeEnd());
        } catch (Exception e) {
            log.error("Error handling AuctionApprovedEvent: {}", e.getMessage(), e);
        }
    }
}
