package com.ghtk.onlinebiddingproject.adapter.out.repository.database.mapper;

import com.ghtk.onlinebiddingproject.adapter.out.repository.database.persistence.*;
import com.ghtk.onlinebiddingproject.domain.model.auction.*;
import com.ghtk.onlinebiddingproject.domain.model.shared.Money;
import jakarta.persistence.EntityManager;

public class AuctionMapper {

    private AuctionMapper() {
    }

    public static Auction toDomain(AuctionEntity entity) {
        if (entity == null) return null;
        Auction auction = new Auction(
                entity.getId(),
                entity.getDescription(),
                entity.getTimeStart(),
                entity.getTimeEnd(),
                Money.of(entity.getPriceStart()),
                Money.of(entity.getPriceStep()),
                entity.getStatus(),
                entity.getUser().getId(),
                entity.getCategory() != null ? entity.getCategory().getId() : null,
                entity.getItem() != null ? entity.getItem().getId() : null
        );
        if (entity.getHighestPrice() != null) {
            auction.setHighestPrice(Money.of(entity.getHighestPrice()));
        }
        return auction;
    }

    public static AuctionEntity toEntity(Auction domain, EntityManager em) {
        if (domain == null) return null;
        AuctionEntity entity = new AuctionEntity();
        entity.setId(domain.getId());
        entity.setDescription(domain.getDescription());
        entity.setTimeStart(domain.getTimeStart());
        entity.setTimeEnd(domain.getTimeEnd());
        entity.setPriceStart(domain.getPriceStart().getAmount());
        entity.setPriceStep(domain.getPriceStep().getAmount());
        entity.setHighestPrice(domain.getHighestPrice() != null ? domain.getHighestPrice().getAmount() : null);
        entity.setStatus(domain.getStatus());
        entity.setUser(em.getReference(UserEntity.class, domain.getOwnerId()));
        entity.setCategory(domain.getCategoryId() != null ? em.getReference(CategoryEntity.class, domain.getCategoryId()) : null);
        return entity;
    }

    public static Bid toDomain(BidEntity entity) {
        if (entity == null) return null;
        return new Bid(
                entity.getId(),
                Money.of(entity.getPrice()),
                entity.getUser().getId(),
                entity.getAuction().getId(),
                entity.getCreatedAt()
        );
    }

    public static BidEntity toEntity(Bid domain, EntityManager em) {
        if (domain == null) return null;
        BidEntity entity = new BidEntity();
        entity.setId(domain.getId());
        entity.setPrice(domain.getPrice().getAmount());
        entity.setUser(em.getReference(UserEntity.class, domain.getUserId()));
        entity.setAuction(em.getReference(AuctionEntity.class, domain.getAuctionId()));
        return entity;
    }

    public static Winner toDomain(WinnerEntity entity) {
        if (entity == null) return null;
        return new Winner(
                entity.getId(),
                entity.getBid().getId(),
                entity.getAuction().getId()
        );
    }

    public static WinnerEntity toEntity(Winner domain, EntityManager em) {
        if (domain == null) return null;
        WinnerEntity entity = new WinnerEntity();
        entity.setId(domain.getId());
        entity.setBid(em.getReference(BidEntity.class, domain.getBidId()));
        entity.setAuction(em.getReference(AuctionEntity.class, domain.getAuctionId()));
        return entity;
    }

    public static Category toDomain(CategoryEntity entity) {
        if (entity == null) return null;
        return new Category(entity.getId(), entity.getName());
    }

    public static CategoryEntity toEntity(Category domain) {
        if (domain == null) return null;
        CategoryEntity entity = new CategoryEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        return entity;
    }


}
