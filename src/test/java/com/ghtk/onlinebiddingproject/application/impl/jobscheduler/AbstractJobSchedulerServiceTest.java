package com.ghtk.onlinebiddingproject.application.impl.jobscheduler;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.ghtk.onlinebiddingproject.application.impl.JobSchedulerServiceImpl;
import com.ghtk.onlinebiddingproject.domain.model.auction.Auction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.quartz.Scheduler;

public abstract class AbstractJobSchedulerServiceTest {

    protected Scheduler scheduler;
    protected JobSchedulerServiceImpl jobSchedulerService;

    @BeforeEach
    void setUp() {
        initDependencies();
        jobSchedulerService = new JobSchedulerServiceImpl(scheduler);
    }

    protected abstract void initDependencies();
    protected abstract Auction createAuction(Integer id);

    @Test
    void shouldScheduleStartAuctionJob() {
        Auction auction = createAuction(1);

        assertDoesNotThrow(() -> jobSchedulerService.startAuctionScheduler(auction));
    }

    @Test
    void shouldScheduleEndAuctionJob() {
        Auction auction = createAuction(1);

        assertDoesNotThrow(() -> jobSchedulerService.endAuctionScheduler(auction));
    }

    @Test
    void shouldScheduleCancelAuctionJob() {
        Auction auction = createAuction(1);

        assertDoesNotThrow(() -> jobSchedulerService.cancelAuctionScheduler(auction));
    }
}
