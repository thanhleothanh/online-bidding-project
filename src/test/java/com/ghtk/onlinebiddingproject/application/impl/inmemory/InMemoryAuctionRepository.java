package com.ghtk.onlinebiddingproject.application.impl.inmemory;

import com.ghtk.onlinebiddingproject.domain.model.auction.Auction;
import com.ghtk.onlinebiddingproject.domain.model.auction.AuctionQuery;
import com.ghtk.onlinebiddingproject.domain.model.auction.AuctionRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryAuctionRepository implements AuctionRepository {

    private final Map<Integer, Auction> auctions = new HashMap<>();
    private final AtomicInteger nextId = new AtomicInteger(1);

    @Override
    public Optional<Auction> findById(Integer id) {
        return Optional.ofNullable(auctions.get(id));
    }

    @Override
    public Auction save(Auction auction) {
        if (auction.getId() == null) {
            int id = nextId.getAndIncrement();
            Auction newAuction = new Auction(id, auction.getDescription(), auction.getTimeStart(),
                    auction.getTimeEnd(), auction.getPriceStart(), auction.getPriceStep(),
                    auction.getStatus(), auction.getOwnerId(), auction.getCategoryId(), auction.getItemId());
            newAuction.setHighestPrice(auction.getHighestPrice());
            auctions.put(id, newAuction);
            return newAuction;
        }
        auctions.put(auction.getId(), auction);
        return auction;
    }

    @Override
    public void deleteById(Integer id) {
        auctions.remove(id);
    }

    @Override
    public List<Auction> find(AuctionQuery query) {
        return auctions.values().stream()
                .filter(a -> query.getOwnerId() == null || a.getOwnerId().equals(query.getOwnerId()))
                .filter(a -> query.getStatus() == null || a.getStatus() == query.getStatus())
                .filter(a -> query.getDescription() == null || a.getDescription().contains(query.getDescription()))
                .filter(a -> query.getCategoryId() == null || a.getCategoryId().equals(query.getCategoryId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Auction> findPaged(AuctionQuery query, int page, int size) {
        return find(query).stream()
                .skip((long) page * size)
                .limit(size)
                .collect(Collectors.toList());
    }
}
