package com.ghtk.onlinebiddingproject.application.impl;

import com.ghtk.onlinebiddingproject.application.AuctionService;
import com.ghtk.onlinebiddingproject.domain.event.AuctionApprovedEvent;
import com.ghtk.onlinebiddingproject.domain.event.AuctionCreatedEvent;
import com.ghtk.onlinebiddingproject.domain.event.EventPublisher;
import com.ghtk.onlinebiddingproject.domain.model.auction.*;
import com.ghtk.onlinebiddingproject.domain.model.shared.Money;

import java.util.List;

public class AuctionServiceImpl implements AuctionService {

    private final AuctionRepository auctionRepository;
    private final BidRepository bidRepository;
    private final WinnerRepository winnerRepository;
    private final EventPublisher eventPublisher;

    public AuctionServiceImpl(AuctionRepository auctionRepository,
                             BidRepository bidRepository,
                             WinnerRepository winnerRepository,
                             EventPublisher eventPublisher) {
        this.auctionRepository = auctionRepository;
        this.bidRepository = bidRepository;
        this.winnerRepository = winnerRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Auction getById(Integer id, Integer userId) {
        return auctionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Auction not found"));
    }

    @Override
    public List<Auction> get(AuctionQuery query, int page, int size) {
        return auctionRepository.findPaged(query, page, size);
    }

    @Override
    public List<Auction> getTopTrending() {
        return auctionRepository.find(AuctionQuery.builder().status(AuctionStatus.OPENING).build());
    }

    @Override
    public List<Auction> getMyAuctions(Integer userId, AuctionStatus status) {
        return auctionRepository.find(AuctionQuery.builder().ownerId(userId).status(status).build());
    }

    @Override
    public List<Auction> getWonAuctionsByUserId(Integer userId) {
        List<Winner> winners = winnerRepository.findByUserId(userId);
        return winners.stream()
                .map(w -> auctionRepository.findById(w.getAuctionId()).orElse(null))
                .filter(a -> a != null)
                .toList();
    }

    @Override
    public List<Auction> getAuctionsByUserId(Integer userId) {
        return auctionRepository.find(AuctionQuery.builder().ownerId(userId).build());
    }

    @Override
    public Auction create(Auction auction, Integer userId) {
        Auction saved = auctionRepository.save(auction);
        eventPublisher.publish(new AuctionCreatedEvent(saved.getId(), userId));
        return saved;
    }

    @Override
    public Auction update(Integer id, Auction auction, Integer userId) {
        Auction existing = auctionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Auction not found"));
        if (!existing.canBeEditedBy(userId)) {
            throw new IllegalStateException("Cannot edit this auction");
        }
        existing.setDescription(auction.getDescription());
        existing.setTimeStart(auction.getTimeStart());
        existing.setTimeEnd(auction.getTimeEnd());
        existing.setHighestPrice(auction.getHighestPrice());
        return auctionRepository.save(existing);
    }

    @Override
    public Auction submitPending(Integer id, Integer userId) {
        Auction auction = auctionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Auction not found"));
        if (!auction.isOwnedBy(userId)) {
            throw new IllegalStateException("Not authorized");
        }
        auction.submitForReview();
        return auctionRepository.save(auction);
    }

    @Override
    public void delete(Integer id, Integer userId) {
        Auction auction = auctionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Auction not found"));
        if (!auction.canBeDeletedBy(userId)) {
            throw new IllegalStateException("Cannot delete this auction");
        }
        auctionRepository.deleteById(id);
    }

    @Override
    public Auction adminGetById(Integer id) {
        return auctionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Auction not found"));
    }

    @Override
    public Auction adminPut(Auction auction, Integer id) {
        Auction existing = auctionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Auction not found"));
        existing.setDescription(auction.getDescription());
        existing.setTimeStart(auction.getTimeStart());
        existing.setTimeEnd(auction.getTimeEnd());
        existing.setHighestPrice(auction.getHighestPrice());
        return auctionRepository.save(existing);
    }

    @Override
    public Auction adminReview(Auction auction, Integer id, Integer adminId) {
        Auction existing = auctionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Auction not found"));
        if (auction.getStatus() == AuctionStatus.QUEUED) {
            existing.approve(adminId);
            eventPublisher.publish(new AuctionApprovedEvent(id, adminId));
        } else if (auction.getStatus() == AuctionStatus.CANCELED) {
            existing.reject(adminId);
        }
        return auctionRepository.save(existing);
    }

    @Override
    public void adminDelete(Integer id) {
        auctionRepository.deleteById(id);
    }
}
