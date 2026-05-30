package com.ghtk.onlinebiddingproject.domain.model.auction;

import com.ghtk.onlinebiddingproject.domain.model.shared.Money;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class BiddingRulesServiceTest {

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
    void shouldValidateValidBid() {
        Auction auction = createOpeningAuction();

        assertDoesNotThrow(() -> BiddingRulesService.validateBid(auction, 2, Money.of(1500.0)));
    }

    @Test
    void shouldRejectBidOnNonOpeningAuction() {
        Auction auction = Auction.create(new AuctionDraft("Test",
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(7),
                1000.0, 100.0, 1, 1), 1);

        assertThrows(IllegalStateException.class,
                () -> BiddingRulesService.validateBid(auction, 2, Money.of(1500.0)));
    }

    @Test
    void shouldRejectBidFromOwner() {
        Auction auction = createOpeningAuction();

        assertThrows(IllegalStateException.class,
                () -> BiddingRulesService.validateBid(auction, 1, Money.of(1500.0)));
    }

    @Test
    void shouldRejectBidBelowStartPrice() {
        Auction auction = createOpeningAuction();

        assertThrows(IllegalArgumentException.class,
                () -> BiddingRulesService.validateBid(auction, 2, Money.of(500.0)));
    }

    @Test
    void shouldRejectBidBelowHighestPricePlusStep() {
        Auction auction = createOpeningAuction();
        auction.placeBid(Money.of(1500.0));

        assertThrows(IllegalArgumentException.class,
                () -> BiddingRulesService.validateBid(auction, 2, Money.of(1550.0)));
    }

    @Test
    void shouldRejectBidExceeding3xWhenHighPrice() {
        Auction auction = createOpeningAuction();
        auction.placeBid(Money.of(150000.0));

        assertThrows(IllegalArgumentException.class,
                () -> BiddingRulesService.validateBid(auction, 2, Money.of(500000.0)));
    }
}
