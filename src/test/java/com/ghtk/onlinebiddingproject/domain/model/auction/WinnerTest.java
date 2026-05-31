package com.ghtk.onlinebiddingproject.domain.model.auction;

import com.ghtk.onlinebiddingproject.domain.model.shared.Money;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WinnerTest {

    private Auction createOpeningAuction() {
        Auction auction = Auction.create(new AuctionDraft("Test",
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(7),
                1000.0, 100.0, 1, 1), 1);
        auction.submitForReview();
        auction.approve(2);
        auction.start();
        return auction;
    }

    @Test
    void shouldDetermineWinnerWithBids() {
        Auction auction = createOpeningAuction();
        auction.setId(1);
        Bid bid1 = new Bid(1, Money.of(1500.0), 2, 1, LocalDateTime.now());
        Bid bid2 = new Bid(2, Money.of(2000.0), 3, 1, LocalDateTime.now());

        Winner winner = Winner.determine(auction, List.of(bid1, bid2));

        assertNotNull(winner);
        assertEquals(2, winner.getBidId());
        assertEquals(1, winner.getAuctionId());
    }

    @Test
    void shouldReturnNullWhenNoBids() {
        Auction auction = createOpeningAuction();

        Winner winner = Winner.determine(auction, List.of());

        assertNull(winner);
    }

    @Test
    void shouldReturnNullWhenNullBids() {
        Auction auction = createOpeningAuction();

        Winner winner = Winner.determine(auction, null);

        assertNull(winner);
    }

    @Test
    void shouldDetermineWinnerWithSingleBid() {
        Auction auction = createOpeningAuction();
        auction.setId(1);
        Bid bid = new Bid(1, Money.of(1500.0), 2, 1, LocalDateTime.now());

        Winner winner = Winner.determine(auction, List.of(bid));

        assertNotNull(winner);
        assertEquals(1, winner.getBidId());
        assertEquals(1, winner.getAuctionId());
    }
}
