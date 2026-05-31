package com.ghtk.onlinebiddingproject.adapter.out.repository.database.mapper;

import com.ghtk.onlinebiddingproject.adapter.out.repository.database.persistence.AuctionEntity;
import com.ghtk.onlinebiddingproject.adapter.out.repository.database.persistence.ItemEntity;
import com.ghtk.onlinebiddingproject.adapter.out.repository.database.persistence.ItemImageEntity;
import com.ghtk.onlinebiddingproject.domain.model.item.Item;
import com.ghtk.onlinebiddingproject.domain.model.item.ItemImage;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

public class ItemMapper {

    private ItemMapper() {
    }

    public static Item toDomain(ItemEntity entity) {
        if (entity == null) return null;
        List<ItemImage> images = new ArrayList<>();
        if (entity.getImages() != null) {
            images = entity.getImages().stream()
                    .map(ItemMapper::toDomainImage)
                    .toList();
        }
        return new Item(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getAmount(),
                entity.getAuction().getId(),
                images
        );
    }

    public static ItemEntity toEntity(Item domain, EntityManager em) {
        if (domain == null) return null;
        ItemEntity entity;
        if (domain.getId() != null) {
            entity = em.find(ItemEntity.class, domain.getId());
            if (entity == null) {
                entity = new ItemEntity();
                entity.setId(domain.getId());
            }
        } else {
            entity = new ItemEntity();
        }
        entity.setName(domain.getName());
        entity.setDescription(domain.getDescription());
        entity.setAmount(domain.getAmount());
        entity.setAuction(em.getReference(AuctionEntity.class, domain.getAuctionId()));
        return entity;
    }

    public static ItemImage toDomainImage(ItemImageEntity entity) {
        if (entity == null) return null;
        return new ItemImage(
                entity.getId(),
                entity.getImageUrl(),
                entity.getPublicId()
        );
    }

    public static ItemImageEntity toEntityImage(ItemImage domain, ItemEntity itemEntity) {
        if (domain == null) return null;
        ItemImageEntity entity = new ItemImageEntity();
        entity.setId(domain.getId());
        entity.setImageUrl(domain.getImageUrl());
        entity.setPublicId(domain.getPublicId());
        entity.setItem(itemEntity);
        return entity;
    }
}
