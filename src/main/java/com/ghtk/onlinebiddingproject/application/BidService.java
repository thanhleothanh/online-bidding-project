package com.ghtk.onlinebiddingproject.application;

import com.ghtk.onlinebiddingproject.domain.model.auction.Bid;
import com.ghtk.onlinebiddingproject.domain.model.shared.Money;

import java.util.List;

public interface BidService {
    List<Bid> getByAuctionId(Integer auctionId);
    Bid placeBid(Integer auctionId, Money amount, Integer userId);
}
