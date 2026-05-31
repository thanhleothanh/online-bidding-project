package com.ghtk.onlinebiddingproject.domain.model.auction;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Comparator;
import java.util.List;

@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Winner {

    private Integer id;
    private Integer bidId;
    private Integer auctionId;

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
        return new Winner(null, highestBid.getId(), auction.getId());
    }

}
