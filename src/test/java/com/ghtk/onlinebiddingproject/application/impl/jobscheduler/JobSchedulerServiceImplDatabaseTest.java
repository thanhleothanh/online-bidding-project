package com.ghtk.onlinebiddingproject.application.impl.jobscheduler;

import com.ghtk.onlinebiddingproject.domain.model.auction.Auction;
import com.ghtk.onlinebiddingproject.domain.model.auction.AuctionDraft;
import com.ghtk.onlinebiddingproject.infrastructure.IntegrationDatabaseTest;
import java.time.LocalDateTime;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationDatabaseTest
class JobSchedulerServiceImplDatabaseTest extends AbstractJobSchedulerServiceTest {

    @Autowired
    private Scheduler autScheduler;

    @Override
    protected void initDependencies() {
        scheduler = autScheduler;
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
}
