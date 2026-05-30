package com.ghtk.onlinebiddingproject.domain.model.auction;

import java.util.List;
import java.util.Optional;

public interface AuctionRepository {
    Optional<Auction> findById(Integer id);
    Auction save(Auction auction);
    void deleteById(Integer id);
    List<Auction> find(AuctionQuery query);
    List<Auction> findPaged(AuctionQuery query, int page, int size);
}
