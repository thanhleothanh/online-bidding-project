package com.ghtk.onlinebiddingproject.domain.model.auction;

import com.ghtk.onlinebiddingproject.domain.model.shared.Money;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AuctionTest {

    private AuctionDraft createValidDraft() {
        return new AuctionDraft("Test Auction",
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(7),
                1000.0, 100.0, 1, 1);
    }

    @Test
    void shouldCreateAuction() {
        Auction auction = Auction.create(createValidDraft(), 1);

        assertNull(auction.getId());
        assertEquals(AuctionStatus.DRAFT, auction.getStatus());
        assertEquals(1, auction.getOwnerId());
        assertEquals(Money.of(1000.0), auction.getPriceStart());
    }

    @Test
    void shouldRejectStartAfterEnd() {
        AuctionDraft draft = new AuctionDraft("Test",
                LocalDateTime.now().plusDays(7),
                LocalDateTime.now().plusDays(1),
                1000.0, 100.0, 1, 1);

        assertThrows(IllegalArgumentException.class, () -> Auction.create(draft, 1));
    }

    @Test
    void shouldRejectPastStartTime() {
        AuctionDraft draft = new AuctionDraft("Test",
                LocalDateTime.now().minusDays(1),
                LocalDateTime.now().plusDays(7),
                1000.0, 100.0, 1, 1);

        assertThrows(IllegalArgumentException.class, () -> Auction.create(draft, 1));
    }

    @Test
    void shouldSubmitForReview() {
        Auction auction = Auction.create(createValidDraft(), 1);

        auction.submitForReview();

        assertEquals(AuctionStatus.PENDING, auction.getStatus());
    }

    @Test
    void shouldRejectSubmitWhenNotDraft() {
        Auction auction = Auction.create(createValidDraft(), 1);
        auction.submitForReview();

        assertThrows(IllegalStateException.class, auction::submitForReview);
    }

    @Test
    void shouldApproveAuction() {
        Auction auction = Auction.create(createValidDraft(), 1);
        auction.submitForReview();

        auction.approve(2);

        assertEquals(AuctionStatus.QUEUED, auction.getStatus());
    }

    @Test
    void shouldRejectApproveWhenNotPending() {
        Auction auction = Auction.create(createValidDraft(), 1);

        assertThrows(IllegalStateException.class, () -> auction.approve(2));
    }

    @Test
    void shouldStartAuction() {
        Auction auction = Auction.create(createValidDraft(), 1);
        auction.submitForReview();
        auction.approve(2);

        auction.start();

        assertEquals(AuctionStatus.OPENING, auction.getStatus());
    }

    @Test
    void shouldEndAuction() {
        Auction auction = Auction.create(createValidDraft(), 1);
        auction.submitForReview();
        auction.approve(2);
        auction.start();

        auction.end();

        assertEquals(AuctionStatus.ENDED, auction.getStatus());
    }

    @Test
    void shouldRejectEndWhenNotOpening() {
        Auction auction = Auction.create(createValidDraft(), 1);

        assertThrows(IllegalStateException.class, auction::end);
    }

    @Test
    void shouldCancelAuction() {
        Auction auction = Auction.create(createValidDraft(), 1);

        auction.cancel();

        assertEquals(AuctionStatus.CANCELED, auction.getStatus());
    }

    @Test
    void shouldRejectCancelWhenEnded() {
        Auction auction = Auction.create(createValidDraft(), 1);
        auction.submitForReview();
        auction.approve(2);
        auction.start();
        auction.end();

        assertThrows(IllegalStateException.class, auction::cancel);
    }

    @Test
    void shouldPlaceBid() {
        Auction auction = Auction.create(createValidDraft(), 1);
        auction.submitForReview();
        auction.approve(2);
        auction.start();

        auction.placeBid(Money.of(1500.0));

        assertEquals(Money.of(1500.0), auction.getHighestPrice());
    }

    @Test
    void shouldRejectBidBelowStartPrice() {
        Auction auction = Auction.create(createValidDraft(), 1);
        auction.submitForReview();
        auction.approve(2);
        auction.start();

        assertThrows(IllegalArgumentException.class, () -> auction.placeBid(Money.of(500.0)));
    }

    @Test
    void shouldCheckOwnership() {
        Auction auction = Auction.create(createValidDraft(), 1);

        assertTrue(auction.isOwnedBy(1));
        assertFalse(auction.isOwnedBy(2));
    }

    @Test
    void shouldCheckCanBeEditedBy() {
        Auction auction = Auction.create(createValidDraft(), 1);

        assertTrue(auction.canBeEditedBy(1));
        assertFalse(auction.canBeEditedBy(2));
    }
}
