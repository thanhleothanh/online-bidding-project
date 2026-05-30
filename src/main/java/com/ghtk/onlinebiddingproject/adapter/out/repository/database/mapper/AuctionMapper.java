package com.ghtk.onlinebiddingproject.adapter.out.repository.database.mapper;

import com.ghtk.onlinebiddingproject.adapter.out.repository.database.persistence.*;
import com.ghtk.onlinebiddingproject.constants.AuctionStatusConstants;
import com.ghtk.onlinebiddingproject.domain.model.auction.*;
import com.ghtk.onlinebiddingproject.domain.model.shared.Money;

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
                toDomainStatus(entity.getStatus()),
                entity.getOwnerId(),
                entity.getCategoryId(),
                entity.getItemId()
        );
        if (entity.getHighestPrice() != null) {
            auction.setHighestPrice(Money.of(entity.getHighestPrice()));
        }
        return auction;
    }

    public static AuctionEntity toEntity(Auction domain) {
        if (domain == null) return null;
        AuctionEntity entity = new AuctionEntity();
        entity.setId(domain.getId());
        entity.setDescription(domain.getDescription());
        entity.setTimeStart(domain.getTimeStart());
        entity.setTimeEnd(domain.getTimeEnd());
        entity.setPriceStart(domain.getPriceStart().getAmount());
        entity.setPriceStep(domain.getPriceStep().getAmount());
        entity.setHighestPrice(domain.getHighestPrice().getAmount());
        entity.setStatus(toEntityStatus(domain.getStatus()));
        entity.setOwnerId(domain.getOwnerId());
        entity.setCategoryId(domain.getCategoryId());
        entity.setItemId(domain.getItemId());
        return entity;
    }

    public static Bid toDomain(BidEntity entity) {
        if (entity == null) return null;
        return new Bid(
                entity.getId(),
                Money.of(entity.getPrice()),
                entity.getUserId(),
                entity.getAuctionId(),
                entity.getBidTime()
        );
    }

    public static BidEntity toEntity(Bid domain) {
        if (domain == null) return null;
        BidEntity entity = new BidEntity();
        entity.setId(domain.getId());
        entity.setPrice(domain.getPrice().getAmount());
        entity.setUserId(domain.getUserId());
        entity.setAuctionId(domain.getAuctionId());
        entity.setBidTime(domain.getBidTime());
        return entity;
    }

    public static Winner toDomain(WinnerEntity entity) {
        if (entity == null) return null;
        return new Winner(
                entity.getId(),
                entity.getBidId(),
                entity.getAuctionId(),
                entity.getUserId()
        );
    }

    public static WinnerEntity toEntity(Winner domain) {
        if (domain == null) return null;
        WinnerEntity entity = new WinnerEntity();
        entity.setId(domain.getId());
        entity.setBidId(domain.getBidId());
        entity.setAuctionId(domain.getAuctionId());
        entity.setUserId(domain.getUserId());
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

    private static AuctionStatus toDomainStatus(AuctionStatusConstants status) {
        if (status == null) return AuctionStatus.DRAFT;
        return switch (status) {
            case DRAFT -> AuctionStatus.DRAFT;
            case PENDING -> AuctionStatus.PENDING;
            case QUEUED -> AuctionStatus.QUEUED;
            case OPENING -> AuctionStatus.OPENING;
            case ENDED -> AuctionStatus.ENDED;
            case CANCELED -> AuctionStatus.CANCELED;
        };
    }

    private static AuctionStatusConstants toEntityStatus(AuctionStatus status) {
        if (status == null) return AuctionStatusConstants.DRAFT;
        return switch (status) {
            case DRAFT -> AuctionStatusConstants.DRAFT;
            case PENDING -> AuctionStatusConstants.PENDING;
            case QUEUED -> AuctionStatusConstants.QUEUED;
            case OPENING -> AuctionStatusConstants.OPENING;
            case ENDED -> AuctionStatusConstants.ENDED;
            case CANCELED -> AuctionStatusConstants.CANCELED;
        };
    }
}
