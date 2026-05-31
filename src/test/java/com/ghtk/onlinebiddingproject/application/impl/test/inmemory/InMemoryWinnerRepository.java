package com.ghtk.onlinebiddingproject.application.impl.test.inmemory;

import com.ghtk.onlinebiddingproject.domain.model.auction.Winner;
import com.ghtk.onlinebiddingproject.domain.model.auction.WinnerRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryWinnerRepository implements WinnerRepository {

    private final Map<Integer, Winner> winners = new HashMap<>();
    private final AtomicInteger nextId = new AtomicInteger(1);

    @Override
    public Winner save(Winner winner) {
        if (winner.getId() == null) {
            int id = nextId.getAndIncrement();
            Winner newWinner = new Winner(id, winner.getBidId(), winner.getAuctionId(), winner.getUserId());
            winners.put(id, newWinner);
            return newWinner;
        }
        winners.put(winner.getId(), winner);
        return winner;
    }

    @Override
    public Optional<Winner> findByAuctionId(Integer auctionId) {
        return winners.values().stream()
                .filter(w -> w.getAuctionId().equals(auctionId))
                .findFirst();
    }

    @Override
    public List<Winner> findByUserId(Integer userId) {
        return winners.values().stream()
                .filter(w -> w.getUserId().equals(userId))
                .collect(Collectors.toList());
    }
}
