package com.ghtk.onlinebiddingproject.application.impl.jobscheduler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ghtk.onlinebiddingproject.application.impl.test.inmemory.InMemoryScheduler;
import com.ghtk.onlinebiddingproject.domain.model.auction.Auction;
import com.ghtk.onlinebiddingproject.domain.model.auction.AuctionDraft;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class JobSchedulerServiceImplInMemoryTest extends AbstractJobSchedulerServiceTest {

    private InMemoryScheduler inMemoryScheduler;

    @Override
    protected void initDependencies() {
        inMemoryScheduler = new InMemoryScheduler();
        inMemoryScheduler.start();
        scheduler = inMemoryScheduler;
    }

    @Override
    protected Auction createAuction(Integer id) {
        AuctionDraft draft = new AuctionDraft("Test Auction",
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(7),
                1000.0, 100.0, 1, 1);
        Auction auction = Auction.create(draft, 1);
        auction.setId(id);
        return auction;
    }

    @Test
    void shouldDeletePendingCancelJobOnStart() {
        Auction auction = createAuction(1);
        jobSchedulerService.cancelAuctionScheduler(auction);
        assertEquals(1, inMemoryScheduler.getScheduledJobs().size());

        jobSchedulerService.startAuctionScheduler(auction);

        assertEquals(1, inMemoryScheduler.getScheduledJobs().size());
    }

    @Test
    void shouldContainAuctionIdInJobDataMap() {
        Auction auction = createAuction(42);

        jobSchedulerService.startAuctionScheduler(auction);

        var jobs = inMemoryScheduler.getScheduledJobs();
        assertEquals(1, jobs.size());
    }
}
