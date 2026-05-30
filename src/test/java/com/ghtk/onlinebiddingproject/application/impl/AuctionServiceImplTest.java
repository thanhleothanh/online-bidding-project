package com.ghtk.onlinebiddingproject.application.impl;

import com.ghtk.onlinebiddingproject.application.impl.inmemory.*;
import com.ghtk.onlinebiddingproject.application.impl.inmemory.InMemoryEventPublisher;
import com.ghtk.onlinebiddingproject.domain.model.auction.*;
import com.ghtk.onlinebiddingproject.domain.model.shared.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AuctionServiceImplTest {

    private AuctionServiceImpl auctionService;
    private InMemoryAuctionRepository auctionRepository;
    private InMemoryBidRepository bidRepository;
    private InMemoryWinnerRepository winnerRepository;
    private InMemoryEventPublisher eventPublisher;

    @BeforeEach
    void setUp() {
        auctionRepository = new InMemoryAuctionRepository();
        bidRepository = new InMemoryBidRepository();
        winnerRepository = new InMemoryWinnerRepository();
        eventPublisher = new InMemoryEventPublisher();

        auctionService = new AuctionServiceImpl(auctionRepository, bidRepository, winnerRepository, eventPublisher);
    }

    private Auction createDraftAuction(Integer ownerId) {
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
        assertEquals(1, eventPublisher.getPublishedEvents().size());
    }

    @Test
    void shouldGetAuctionById() {
        Auction auction = createDraftAuction(1);
        Auction created = auctionService.create(auction, 1);

        Auction found = auctionService.getById(created.getId(), 1);

        assertEquals(created.getId(), found.getId());
    }

    @Test
    void shouldSubmitPending() {
        Auction auction = createDraftAuction(1);
        Auction created = auctionService.create(auction, 1);

        Auction submitted = auctionService.submitPending(created.getId(), 1);

        assertEquals(AuctionStatus.PENDING, submitted.getStatus());
    }

    @Test
    void shouldRejectSubmitPendingWhenNotOwner() {
        Auction auction = createDraftAuction(1);
        Auction created = auctionService.create(auction, 1);

        assertThrows(IllegalStateException.class,
                () -> auctionService.submitPending(created.getId(), 2));
    }

    @Test
    void shouldAdminReviewApprove() {
        Auction auction = createDraftAuction(1);
        Auction created = auctionService.create(auction, 1);
        auctionService.submitPending(created.getId(), 1);

        Auction reviewed = auctionService.adminReview(
                new Auction(null, null, null, null, null, null, AuctionStatus.QUEUED, null, null, null),
                created.getId(), 2);

        assertEquals(AuctionStatus.QUEUED, reviewed.getStatus());
    }

    @Test
    void shouldAdminReviewReject() {
        Auction auction = createDraftAuction(1);
        Auction created = auctionService.create(auction, 1);
        auctionService.submitPending(created.getId(), 1);

        Auction reviewed = auctionService.adminReview(
                new Auction(null, null, null, null, null, null, AuctionStatus.CANCELED, null, null, null),
                created.getId(), 2);

        assertEquals(AuctionStatus.CANCELED, reviewed.getStatus());
    }

    @Test
    void shouldDeleteAuction() {
        Auction auction = createDraftAuction(1);
        Auction created = auctionService.create(auction, 1);

        assertDoesNotThrow(() -> auctionService.delete(created.getId(), 1));
        assertTrue(auctionRepository.findById(created.getId()).isEmpty());
    }

    @Test
    void shouldRejectDeleteWhenNotOwner() {
        Auction auction = createDraftAuction(1);
        Auction created = auctionService.create(auction, 1);

        assertThrows(IllegalStateException.class,
                () -> auctionService.delete(created.getId(), 2));
    }

    @Test
    void shouldGetMyAuctions() {
        Auction auction1 = createDraftAuction(1);
        Auction auction2 = createDraftAuction(1);
        auctionService.create(auction1, 1);
        auctionService.create(auction2, 1);

        var myAuctions = auctionService.getMyAuctions(1, AuctionStatus.DRAFT);

        assertEquals(2, myAuctions.size());
    }
}
