package com.ghtk.onlinebiddingproject.application.impl.auction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.ghtk.onlinebiddingproject.application.impl.test.inmemory.InMemoryEventPublisher;
import com.ghtk.onlinebiddingproject.application.impl.test.inmemory.factories.TestPortFactory;
import com.ghtk.onlinebiddingproject.application.impl.test.inmemory.factories.TestRepositoryFactory;
import com.ghtk.onlinebiddingproject.domain.model.auction.Auction;
import com.ghtk.onlinebiddingproject.domain.model.auction.AuctionDraft;
import com.ghtk.onlinebiddingproject.domain.model.auction.AuctionStatus;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class AuctionServiceImplInMemoryTest extends AbstractAuctionServiceTest {

    @Override
    protected void initDependencies() {
        auctionRepository = TestRepositoryFactory.createAuctionRepository();
        bidRepository = TestRepositoryFactory.createBidRepository();
        winnerRepository = TestRepositoryFactory.createWinnerRepository();
        eventPublisher = TestPortFactory.createEventPublisher();
    }

    private InMemoryEventPublisher events() {
        return (InMemoryEventPublisher) eventPublisher;
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
        assertEquals(1, events().getPublishedEvents().size());
    }
}
