package com.ghtk.onlinebiddingproject.application.impl;

import com.ghtk.onlinebiddingproject.application.impl.inmemory.*;
import com.ghtk.onlinebiddingproject.application.impl.inmemory.InMemoryEventPublisher;
import com.ghtk.onlinebiddingproject.domain.model.auction.*;
import com.ghtk.onlinebiddingproject.domain.model.shared.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class BidServiceImplTest {

    private BidServiceImpl bidService;
    private InMemoryAuctionRepository auctionRepository;
    private InMemoryBidRepository bidRepository;
    private InMemoryEventPublisher eventPublisher;

    @BeforeEach
    void setUp() {
        auctionRepository = new InMemoryAuctionRepository();
        bidRepository = new InMemoryBidRepository();
        eventPublisher = new InMemoryEventPublisher();

        bidService = new BidServiceImpl(bidRepository, auctionRepository, eventPublisher);
    }

    private Auction createOpeningAuction(Integer ownerId) {
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
        assertEquals(1, eventPublisher.getPublishedEvents().size());
    }

    @Test
    void shouldRejectBidOnNonOpeningAuction() {
        AuctionDraft draft = new AuctionDraft("Test",
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(7),
                1000.0, 100.0, 1, 1);
        Auction auction = Auction.create(draft, 1);
        Auction saved = auctionRepository.save(auction);

        assertThrows(IllegalStateException.class,
                () -> bidService.placeBid(saved.getId(), Money.of(1500.0), 2));
    }

    @Test
    void shouldRejectBidFromOwner() {
        Auction auction = createOpeningAuction(1);

        assertThrows(IllegalStateException.class,
                () -> bidService.placeBid(auction.getId(), Money.of(1500.0), 1));
    }

    @Test
    void shouldRejectBidBelowStartPrice() {
        Auction auction = createOpeningAuction(1);

        assertThrows(IllegalArgumentException.class,
                () -> bidService.placeBid(auction.getId(), Money.of(500.0), 2));
    }

    @Test
    void shouldGetBidsByAuctionId() {
        Auction auction = createOpeningAuction(1);
        bidService.placeBid(auction.getId(), Money.of(1500.0), 2);
        bidService.placeBid(auction.getId(), Money.of(2000.0), 3);

        var bids = bidService.getByAuctionId(auction.getId());

        assertEquals(2, bids.size());
    }
}
