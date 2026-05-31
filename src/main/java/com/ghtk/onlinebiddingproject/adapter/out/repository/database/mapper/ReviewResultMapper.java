package com.ghtk.onlinebiddingproject.adapter.out.repository.database.mapper;

import com.ghtk.onlinebiddingproject.adapter.out.repository.database.persistence.AdminEntity;
import com.ghtk.onlinebiddingproject.adapter.out.repository.database.persistence.AuctionEntity;
import com.ghtk.onlinebiddingproject.adapter.out.repository.database.persistence.ReviewResultEntity;
import com.ghtk.onlinebiddingproject.domain.model.review.ReviewResult;
import jakarta.persistence.EntityManager;

public class ReviewResultMapper {

    private ReviewResultMapper() {
    }

    public static ReviewResult toDomain(ReviewResultEntity entity) {
        if (entity == null) return null;
        return new ReviewResult(
                entity.getId(),
                entity.getResult(),
                entity.getAdmin().getId(),
                entity.getAuction().getId()
        );
    }

    public static ReviewResultEntity toEntity(ReviewResult domain, EntityManager em) {
        if (domain == null) return null;
        ReviewResultEntity entity = new ReviewResultEntity();
        entity.setId(domain.getId());
        entity.setResult(domain.getStatus());
        entity.setAdmin(em.getReference(AdminEntity.class, domain.getAdminId()));
        entity.setAuction(em.getReference(AuctionEntity.class, domain.getAuctionId()));
        return entity;
    }
}
