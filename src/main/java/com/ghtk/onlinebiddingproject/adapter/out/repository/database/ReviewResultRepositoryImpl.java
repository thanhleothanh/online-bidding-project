package com.ghtk.onlinebiddingproject.adapter.out.repository.database;

import com.ghtk.onlinebiddingproject.adapter.out.repository.database.mapper.ReviewResultMapper;
import com.ghtk.onlinebiddingproject.adapter.out.repository.database.persistence.ReviewResultEntity;
import com.ghtk.onlinebiddingproject.adapter.out.repository.database.springdata.ReviewResultJpaRepository;
import com.ghtk.onlinebiddingproject.domain.model.review.ReviewResult;
import com.ghtk.onlinebiddingproject.domain.model.review.ReviewResultRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public class ReviewResultRepositoryImpl implements ReviewResultRepository {

    private final ReviewResultJpaRepository reviewResultJpaRepository;
    private final EntityManager entityManager;

    public ReviewResultRepositoryImpl(ReviewResultJpaRepository reviewResultJpaRepository, EntityManager entityManager) {
        this.reviewResultJpaRepository = reviewResultJpaRepository;
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public ReviewResult save(ReviewResult result) {
        ReviewResultEntity entity = ReviewResultMapper.toEntity(result, entityManager);
        ReviewResultEntity saved = reviewResultJpaRepository.save(entity);
        return ReviewResultMapper.toDomain(saved);
    }

    @Override
    public Optional<ReviewResult> findByAuctionId(Integer auctionId) {
        return reviewResultJpaRepository.findByAuctionId(auctionId)
                .map(ReviewResultMapper::toDomain);
    }
}
