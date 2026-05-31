package com.ghtk.onlinebiddingproject.application.impl.bid;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.ghtk.onlinebiddingproject.domain.event.EventPublisher;
import com.ghtk.onlinebiddingproject.domain.model.auction.Auction;
import com.ghtk.onlinebiddingproject.domain.model.auction.AuctionDraft;
import com.ghtk.onlinebiddingproject.domain.model.auction.AuctionRepository;
import com.ghtk.onlinebiddingproject.domain.model.auction.Bid;
import com.ghtk.onlinebiddingproject.domain.model.auction.BidRepository;
import com.ghtk.onlinebiddingproject.domain.model.shared.Money;
import com.ghtk.onlinebiddingproject.infrastructure.IntegrationDatabaseTest;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationDatabaseTest
class BidServiceImplDatabaseTest extends AbstractBidServiceTest {

    @Autowired
    private AuctionRepository autAuctionRepository;

    @Autowired
    private BidRepository autBidRepository;

    @Autowired
    private EventPublisher autEventPublisher;

    @Override
    protected void initDependencies() {
        auctionRepository = autAuctionRepository;
        bidRepository = autBidRepository;
        eventPublisher = autEventPublisher;
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
    }
}
