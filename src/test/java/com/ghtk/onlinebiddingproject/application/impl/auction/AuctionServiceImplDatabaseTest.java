package com.ghtk.onlinebiddingproject.application.impl.auction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.ghtk.onlinebiddingproject.domain.event.EventPublisher;
import com.ghtk.onlinebiddingproject.domain.model.auction.Auction;
import com.ghtk.onlinebiddingproject.domain.model.auction.AuctionDraft;
import com.ghtk.onlinebiddingproject.domain.model.auction.AuctionRepository;
import com.ghtk.onlinebiddingproject.domain.model.auction.AuctionStatus;
import com.ghtk.onlinebiddingproject.domain.model.auction.BidRepository;
import com.ghtk.onlinebiddingproject.domain.model.auction.WinnerRepository;
import com.ghtk.onlinebiddingproject.infrastructure.IntegrationDatabaseTest;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationDatabaseTest
class AuctionServiceImplDatabaseTest extends AbstractAuctionServiceTest {

    @Autowired
    private AuctionRepository autAuctionRepository;

    @Autowired
    private BidRepository autBidRepository;

    @Autowired
    private WinnerRepository autWinnerRepository;

    @Autowired
    private EventPublisher autEventPublisher;

    @Override
    protected void initDependencies() {
        auctionRepository = autAuctionRepository;
        bidRepository = autBidRepository;
        winnerRepository = autWinnerRepository;
        eventPublisher = autEventPublisher;
    }

    @Override
    protected Auction createDraftAuction(Integer ownerId) {
        AuctionDraft draft = new AuctionDraft("Test Auction",
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(7),
                1000.0, 100.0, 1, 1);
        return Auction.create(draft, ownerId);
    }

    @Test
    void shouldCreateAuction() {
        Auction auction = createDraftAuction(1);

        Auction created = auctionService.create(auction, 1);

        assertNotNull(created.getId());
        assertEquals(AuctionStatus.DRAFT, created.getStatus());
    }
}
