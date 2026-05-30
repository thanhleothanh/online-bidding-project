package com.ghtk.onlinebiddingproject.domain.model.auction;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Winner {

    private Integer id;
    private Integer bidId;
    private Integer auctionId;
    private Integer userId;

    public Winner(Integer id, Integer bidId, Integer auctionId, Integer userId) {
        this.id = id;
        this.bidId = bidId;
        this.auctionId = auctionId;
        this.userId = userId;
    }

    public static Winner determine(Auction auction, List<Bid> bids) {
        if (bids == null || bids.isEmpty()) {
            return null;
        }
        Bid highestBid = bids.stream()
                .max(Comparator.comparing(Bid::getPrice))
                .orElse(null);
        if (highestBid == null) {
            return null;
        }
        return new Winner(null, highestBid.getId(), auction.getId(), highestBid.getUserId());
    }

    public Integer getId() { return id; }
    public Integer getBidId() { return bidId; }
    public Integer getAuctionId() { return auctionId; }
    public Integer getUserId() { return userId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Winner winner = (Winner) o;
        return Objects.equals(id, winner.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
