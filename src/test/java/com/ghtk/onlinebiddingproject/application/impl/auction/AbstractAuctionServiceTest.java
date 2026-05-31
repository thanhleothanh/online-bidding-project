package com.ghtk.onlinebiddingproject.application.impl.auction;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ghtk.onlinebiddingproject.application.impl.AuctionServiceImpl;
import com.ghtk.onlinebiddingproject.domain.event.EventPublisher;
import com.ghtk.onlinebiddingproject.domain.model.auction.Auction;
import com.ghtk.onlinebiddingproject.domain.model.auction.AuctionQuery;
import com.ghtk.onlinebiddingproject.domain.model.auction.AuctionRepository;
import com.ghtk.onlinebiddingproject.domain.model.auction.AuctionStatus;
import com.ghtk.onlinebiddingproject.domain.model.auction.BidRepository;
import com.ghtk.onlinebiddingproject.domain.model.auction.WinnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public abstract class AbstractAuctionServiceTest {

    protected AuctionRepository auctionRepository;
    protected BidRepository bidRepository;
    protected WinnerRepository winnerRepository;
    protected EventPublisher eventPublisher;
    protected AuctionServiceImpl auctionService;

    @BeforeEach
    void setUp() {
        initDependencies();
        auctionService = new AuctionServiceImpl(auctionRepository, bidRepository, winnerRepository, eventPublisher);
    }

    protected abstract void initDependencies();
    protected abstract Auction createDraftAuction(Integer ownerId);

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
        auctionService.create(createDraftAuction(1), 1);
        auctionService.create(createDraftAuction(1), 1);

        var myAuctions = auctionService.getMyAuctions(1, AuctionStatus.DRAFT);

        assertEquals(2, myAuctions.size());
    }

    @Test
    void shouldGetAuctionsWithQuery() {
        auctionService.create(createDraftAuction(1), 1);
        auctionService.create(createDraftAuction(2), 2);

        var query = AuctionQuery.builder().userId(1).build();
        var auctions = auctionService.get(query, 0, 10);

        assertEquals(1, auctions.size());
    }
}
