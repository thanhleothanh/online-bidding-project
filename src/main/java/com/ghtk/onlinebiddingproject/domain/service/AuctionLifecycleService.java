package com.ghtk.onlinebiddingproject.domain.service;

import com.ghtk.onlinebiddingproject.domain.model.auction.*;
import com.ghtk.onlinebiddingproject.domain.model.shared.Money;

import java.util.List;
import java.util.Optional;

public class AuctionLifecycleService {

    private final AuctionRepository auctionRepository;
    private final BidRepository bidRepository;
    private final WinnerRepository winnerRepository;

    public AuctionLifecycleService(AuctionRepository auctionRepository,
                                   BidRepository bidRepository,
                                   WinnerRepository winnerRepository) {
        this.auctionRepository = auctionRepository;
        this.bidRepository = bidRepository;
        this.winnerRepository = winnerRepository;
    }

    public Auction startAuction(Integer auctionId) {
        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new RuntimeException("Auction not found"));
        auction.start();
        return auctionRepository.save(auction);
    }

    public Optional<Auction> endAuction(Integer auctionId) {
        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new RuntimeException("Auction not found"));

        List<Bid> bids = bidRepository.findByAuctionId(auctionId);
        if (!bids.isEmpty()) {
            auction.end();
            auctionRepository.save(auction);

            Winner winner = Winner.determine(auction, bids);
            winnerRepository.save(winner);
            return Optional.of(auction);
        } else {
            auction.cancel();
            auctionRepository.save(auction);
            return Optional.empty();
        }
    }

    public Auction cancelAuction(Integer auctionId) {
        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new RuntimeException("Auction not found"));
        auction.cancel();
        return auctionRepository.save(auction);
    }

    public boolean hasBids(Integer auctionId) {
        List<Bid> bids = bidRepository.findByAuctionId(auctionId);
        return !bids.isEmpty();
    }

    public Optional<Winner> getWinner(Integer auctionId) {
        return winnerRepository.findByAuctionId(auctionId);
    }
}
