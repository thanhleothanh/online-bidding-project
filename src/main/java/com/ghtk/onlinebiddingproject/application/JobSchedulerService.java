package com.ghtk.onlinebiddingproject.application;

import com.ghtk.onlinebiddingproject.domain.model.auction.Auction;

public interface JobSchedulerService {
    void startAuctionScheduler(Auction auction);
    void endAuctionScheduler(Auction auction);
    void cancelAuctionScheduler(Auction auction);
}
