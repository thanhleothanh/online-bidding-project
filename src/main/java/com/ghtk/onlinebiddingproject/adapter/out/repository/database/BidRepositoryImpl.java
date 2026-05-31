package com.ghtk.onlinebiddingproject.adapter.out.repository.database;

import com.ghtk.onlinebiddingproject.adapter.out.repository.database.mapper.AuctionMapper;
import com.ghtk.onlinebiddingproject.adapter.out.repository.database.persistence.BidEntity;
import com.ghtk.onlinebiddingproject.adapter.out.repository.database.springdata.BidJpaRepository;
import com.ghtk.onlinebiddingproject.domain.model.auction.Bid;
import com.ghtk.onlinebiddingproject.domain.model.auction.BidRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class BidRepositoryImpl implements BidRepository {

    private final BidJpaRepository bidJpaRepository;
    private final EntityManager entityManager;

    public BidRepositoryImpl(BidJpaRepository bidJpaRepository, EntityManager entityManager) {
        this.bidJpaRepository = bidJpaRepository;
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Bid> findById(Integer id) {
        return bidJpaRepository.findById(id).map(AuctionMapper::toDomain);
    }

    @Override
    @Transactional
    public Bid save(Bid bid) {
        BidEntity entity = AuctionMapper.toEntity(bid, entityManager);
        BidEntity saved = bidJpaRepository.save(entity);
        return AuctionMapper.toDomain(saved);
    }

    @Override
    public List<Bid> findByAuctionId(Integer auctionId) {
        return bidJpaRepository.findByAuctionId(auctionId).stream()
                .map(AuctionMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Bid> findHighestBid(Integer auctionId) {
        return bidJpaRepository.findTopByAuctionIdOrderByPriceDesc(auctionId)
                .map(AuctionMapper::toDomain);
    }
}
