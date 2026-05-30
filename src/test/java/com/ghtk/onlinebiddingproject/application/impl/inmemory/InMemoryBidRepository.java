package com.ghtk.onlinebiddingproject.application.impl.inmemory;

import com.ghtk.onlinebiddingproject.domain.model.auction.Bid;
import com.ghtk.onlinebiddingproject.domain.model.auction.BidRepository;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryBidRepository implements BidRepository {

    private final Map<Integer, Bid> bids = new HashMap<>();
    private final AtomicInteger nextId = new AtomicInteger(1);

    @Override
    public Optional<Bid> findById(Integer id) {
        return Optional.ofNullable(bids.get(id));
    }

    @Override
    public Bid save(Bid bid) {
        if (bid.getId() == null) {
            int id = nextId.getAndIncrement();
            Bid newBid = new Bid(id, bid.getPrice(), bid.getUserId(), bid.getAuctionId(), bid.getBidTime());
            bids.put(id, newBid);
            return newBid;
        }
        bids.put(bid.getId(), bid);
        return bid;
    }

    @Override
    public List<Bid> findByAuctionId(Integer auctionId) {
        return bids.values().stream()
                .filter(b -> b.getAuctionId().equals(auctionId))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Bid> findHighestBid(Integer auctionId) {
        return bids.values().stream()
                .filter(b -> b.getAuctionId().equals(auctionId))
                .max(Comparator.comparing(Bid::getPrice));
    }
}
