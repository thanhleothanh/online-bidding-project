package com.ghtk.onlinebiddingproject.domain.model.auction;

import java.util.List;
import java.util.Optional;

public interface BidRepository {
    Optional<Bid> findById(Integer id);
    Bid save(Bid bid);
    List<Bid> findByAuctionId(Integer auctionId);
    Optional<Bid> findHighestBid(Integer auctionId);
}
