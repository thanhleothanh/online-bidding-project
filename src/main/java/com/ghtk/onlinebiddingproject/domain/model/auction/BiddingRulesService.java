package com.ghtk.onlinebiddingproject.domain.model.auction;

import com.ghtk.onlinebiddingproject.domain.model.shared.Money;

public class BiddingRulesService {

    private BiddingRulesService() {
    }

    public static void validateBid(Auction auction, Integer userId, Money amount) {
        if (!auction.getStatus().canBeBiddedOn()) {
            throw new IllegalStateException("Auction is not open for bidding");
        }
        if (auction.isOwnedBy(userId)) {
            throw new IllegalStateException("Cannot bid on your own auction");
        }
        if (amount.isLessThan(auction.getPriceStart())) {
            throw new IllegalArgumentException("Bid must be at least the starting price");
        }
        Money minimumBid = auction.getHighestPrice().add(auction.getPriceStep());
        if (amount.isLessThan(minimumBid)) {
            throw new IllegalArgumentException("Bid must be at least highest price + step");
        }
        if (auction.getHighestPrice().isGreaterThanOrEqualTo(Money.of(100000))) {
            Money maxAllowed = auction.getHighestPrice().multiply(3);
            if (amount.isGreaterThan(maxAllowed)) {
                throw new IllegalArgumentException("Bid cannot exceed 3x the highest price when highest price >= 100,000");
            }
        }
    }
}
