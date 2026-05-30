package com.ghtk.onlinebiddingproject.adapter.out.repository.database;

import com.ghtk.onlinebiddingproject.adapter.out.repository.database.mapper.AuctionMapper;
import com.ghtk.onlinebiddingproject.adapter.out.repository.database.persistence.WinnerEntity;
import com.ghtk.onlinebiddingproject.adapter.out.repository.database.springdata.WinnerJpaRepository;
import com.ghtk.onlinebiddingproject.domain.model.auction.Winner;
import com.ghtk.onlinebiddingproject.domain.model.auction.WinnerRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class WinnerRepositoryImpl implements WinnerRepository {

    private final WinnerJpaRepository winnerJpaRepository;

    public WinnerRepositoryImpl(WinnerJpaRepository winnerJpaRepository) {
        this.winnerJpaRepository = winnerJpaRepository;
    }

    @Override
    @Transactional
    public Winner save(Winner winner) {
        WinnerEntity entity = AuctionMapper.toEntity(winner);
        WinnerEntity saved = winnerJpaRepository.save(entity);
        return AuctionMapper.toDomain(saved);
    }

    @Override
    public Optional<Winner> findByAuctionId(Integer auctionId) {
        return winnerJpaRepository.findByAuctionId(auctionId)
                .map(AuctionMapper::toDomain);
    }

    @Override
    public List<Winner> findByUserId(Integer userId) {
        return winnerJpaRepository.findByUserId(userId).stream()
                .map(AuctionMapper::toDomain)
                .toList();
    }
}
