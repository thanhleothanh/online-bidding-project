package com.ghtk.onlinebiddingproject.application.impl.bid;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.ghtk.onlinebiddingproject.application.impl.test.inmemory.InMemoryEventPublisher;
import com.ghtk.onlinebiddingproject.application.impl.test.inmemory.factories.TestPortFactory;
import com.ghtk.onlinebiddingproject.application.impl.test.inmemory.factories.TestRepositoryFactory;
import com.ghtk.onlinebiddingproject.domain.model.auction.Auction;
import com.ghtk.onlinebiddingproject.domain.model.auction.AuctionDraft;
import com.ghtk.onlinebiddingproject.domain.model.auction.Bid;
import com.ghtk.onlinebiddingproject.domain.model.shared.Money;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class BidServiceImplInMemoryTest extends AbstractBidServiceTest {

    @Override
    protected void initDependencies() {
        auctionRepository = TestRepositoryFactory.createAuctionRepository();
        bidRepository = TestRepositoryFactory.createBidRepository();
        eventPublisher = TestPortFactory.createEventPublisher();
    }

    private InMemoryEventPublisher events() {
        return (InMemoryEventPublisher) eventPublisher;
    }

    @Override
    protected Auction createOpeningAuction(Integer ownerId) {
        AuctionDraft draft = new AuctionDraft("Test Auction",
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(7),
                1000.0, 100.0, 1, 1);
        Auction auction = Auction.create(draft, ownerId);
        Auction saved = auctionRepository.save(auction);
        saved.submitForReview();
        auctionRepository.save(saved);
        Auction approved = auctionRepository.findById(saved.getId()).orElseThrow();
        approved.approve(2);
        auctionRepository.save(approved);
        Auction started = auctionRepository.findById(saved.getId()).orElseThrow();
        started.start();
        return auctionRepository.save(started);
    }

    @Test
    void shouldPlaceBid() {
        Auction auction = createOpeningAuction(1);

        Bid bid = bidService.placeBid(auction.getId(), Money.of(1500.0), 2);

        assertNotNull(bid.getId());
        assertEquals(Money.of(1500.0), bid.getPrice());
        assertEquals(1, events().getPublishedEvents().size());
    }

    @Test
    void shouldVerifyEventPublished() {
        Auction auction = createOpeningAuction(1);

        bidService.placeBid(auction.getId(), Money.of(1500.0), 2);

        assertEquals(1, events().getPublishedEvents().size());
    }
}
