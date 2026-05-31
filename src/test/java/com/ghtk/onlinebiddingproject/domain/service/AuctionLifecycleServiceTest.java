package com.ghtk.onlinebiddingproject.domain.service;

import com.ghtk.onlinebiddingproject.application.impl.test.inmemory.InMemoryAuctionRepository;
import com.ghtk.onlinebiddingproject.application.impl.test.inmemory.InMemoryBidRepository;
import com.ghtk.onlinebiddingproject.application.impl.test.inmemory.InMemoryWinnerRepository;
import com.ghtk.onlinebiddingproject.domain.model.auction.*;
import com.ghtk.onlinebiddingproject.domain.model.shared.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AuctionLifecycleServiceTest {

    private InMemoryAuctionRepository auctionRepository;
    private InMemoryBidRepository bidRepository;
    private InMemoryWinnerRepository winnerRepository;
    private AuctionLifecycleService lifecycleService;

    @BeforeEach
    void setUp() {
        auctionRepository = new InMemoryAuctionRepository();
        bidRepository = new InMemoryBidRepository();
        winnerRepository = new InMemoryWinnerRepository();
        lifecycleService = new AuctionLifecycleService(auctionRepository, bidRepository, winnerRepository);
    }

    private Auction createAndApproveAuction(Integer ownerId) {
        Auction auction = Auction.create(new AuctionDraft("Test Auction",
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(7),
                1000.0, 100.0, 1, 1), ownerId);
        auction.submitForReview();
        auction.approve(99);
        return auctionRepository.save(auction);
    }

    @Test
    void shouldStartAuction() {
        Auction auction = createAndApproveAuction(1);

        Auction started = lifecycleService.startAuction(auction.getId());

        assertEquals(AuctionStatus.OPENING, started.getStatus());
    }

    @Test
    void shouldEndAuctionWithBidsAndDetermineWinner() {
        Auction auction = createAndApproveAuction(1);
        lifecycleService.startAuction(auction.getId());

        Bid bid1 = Bid.create(auction, 2, Money.of(1500.0));
        bidRepository.save(bid1);
        Bid bid2 = Bid.create(auction, 3, Money.of(2000.0));
        bidRepository.save(bid2);

        Optional<Auction> result = lifecycleService.endAuction(auction.getId());

        assertTrue(result.isPresent());
        assertEquals(AuctionStatus.ENDED, result.get().getStatus());

        Optional<Winner> winner = winnerRepository.findByAuctionId(auction.getId());
        assertTrue(winner.isPresent());
        assertEquals(3, winner.get().getUserId());
    }

    @Test
    void shouldCancelAuctionWithNoBids() {
        Auction auction = createAndApproveAuction(1);
        lifecycleService.startAuction(auction.getId());

        Optional<Auction> result = lifecycleService.endAuction(auction.getId());

        assertTrue(result.isEmpty());
        Auction ended = auctionRepository.findById(auction.getId()).orElseThrow();
        assertEquals(AuctionStatus.CANCELED, ended.getStatus());
    }

    @Test
    void shouldCancelAuction() {
        Auction auction = createAndApproveAuction(1);

        Auction cancelled = lifecycleService.cancelAuction(auction.getId());

        assertEquals(AuctionStatus.CANCELED, cancelled.getStatus());
    }

    @Test
    void shouldCheckIfAuctionHasBids() {
        Auction auction = createAndApproveAuction(1);
        lifecycleService.startAuction(auction.getId());

        assertFalse(lifecycleService.hasBids(auction.getId()));

        Bid bid = Bid.create(auction, 2, Money.of(1500.0));
        bidRepository.save(bid);

        assertTrue(lifecycleService.hasBids(auction.getId()));
    }

    @Test
    void shouldGetWinner() {
        Auction auction = createAndApproveAuction(1);
        lifecycleService.startAuction(auction.getId());

        Bid bid = Bid.create(auction, 2, Money.of(1500.0));
        bidRepository.save(bid);

        lifecycleService.endAuction(auction.getId());

        Optional<Winner> winner = lifecycleService.getWinner(auction.getId());
        assertTrue(winner.isPresent());
        assertEquals(2, winner.get().getUserId());
    }
}
