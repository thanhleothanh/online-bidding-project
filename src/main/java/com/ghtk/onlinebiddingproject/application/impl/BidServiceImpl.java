package com.ghtk.onlinebiddingproject.application.impl;

import com.ghtk.onlinebiddingproject.application.BidService;
import com.ghtk.onlinebiddingproject.domain.event.BidPlacedEvent;
import com.ghtk.onlinebiddingproject.domain.event.EventPublisher;
import com.ghtk.onlinebiddingproject.domain.model.auction.Auction;
import com.ghtk.onlinebiddingproject.domain.model.auction.AuctionRepository;
import com.ghtk.onlinebiddingproject.domain.model.auction.Bid;
import com.ghtk.onlinebiddingproject.domain.model.auction.BidRepository;
import com.ghtk.onlinebiddingproject.domain.model.shared.Money;

import java.util.List;

public class BidServiceImpl implements BidService {

    private final BidRepository bidRepository;
    private final AuctionRepository auctionRepository;
    private final EventPublisher eventPublisher;

    public BidServiceImpl(BidRepository bidRepository,
                         AuctionRepository auctionRepository,
                         EventPublisher eventPublisher) {
        this.bidRepository = bidRepository;
        this.auctionRepository = auctionRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public List<Bid> getByAuctionId(Integer auctionId) {
        return bidRepository.findByAuctionId(auctionId);
    }

    @Override
    public Bid placeBid(Integer auctionId, Money amount, Integer userId) {
        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new RuntimeException("Auction not found"));

        Bid bid = Bid.create(auction, userId, amount);
        auction.placeBid(amount);

        auctionRepository.save(auction);
        Bid savedBid = bidRepository.save(bid);

        eventPublisher.publish(new BidPlacedEvent(auctionId, savedBid.getId(), userId, amount));

        return savedBid;
    }
}
