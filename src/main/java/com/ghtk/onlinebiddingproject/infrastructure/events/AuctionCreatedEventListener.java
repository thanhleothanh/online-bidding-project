package com.ghtk.onlinebiddingproject.infrastructure.events;

import com.ghtk.onlinebiddingproject.application.JobSchedulerService;
import com.ghtk.onlinebiddingproject.domain.event.AuctionCreatedEvent;
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
public class AuctionCreatedEventListener {

    private final JobSchedulerService jobSchedulerService;
    private final AuctionRepository auctionRepository;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleAuctionCreated(AuctionCreatedEvent event) {
        try {
            Auction auction = auctionRepository.findById(event.auctionId())
                    .orElseThrow(() -> new RuntimeException("Auction not found: " + event.auctionId()));
            
            // Schedule a cancel job as a safety net in case auction is not approved by timeStart
            jobSchedulerService.cancelAuctionScheduler(auction);
            
            log.info("Scheduled cancel job for auction {} at {}", auction.getId(), auction.getTimeStart());
        } catch (Exception e) {
            log.error("Error handling AuctionCreatedEvent: {}", e.getMessage(), e);
        }
    }
}
