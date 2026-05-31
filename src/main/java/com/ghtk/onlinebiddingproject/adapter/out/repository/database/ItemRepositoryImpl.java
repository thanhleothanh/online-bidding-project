package com.ghtk.onlinebiddingproject.adapter.out.repository.database;

import com.ghtk.onlinebiddingproject.adapter.out.repository.database.mapper.ItemMapper;
import com.ghtk.onlinebiddingproject.adapter.out.repository.database.persistence.ItemEntity;
import com.ghtk.onlinebiddingproject.adapter.out.repository.database.springdata.ItemJpaRepository;
import com.ghtk.onlinebiddingproject.domain.model.item.Item;
import com.ghtk.onlinebiddingproject.domain.model.item.ItemRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class ItemRepositoryImpl implements ItemRepository {

    private final ItemJpaRepository itemJpaRepository;
    private final EntityManager entityManager;

    public ItemRepositoryImpl(ItemJpaRepository itemJpaRepository, EntityManager entityManager) {
        this.itemJpaRepository = itemJpaRepository;
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Item> findById(Integer id) {
        return itemJpaRepository.findById(id).map(ItemMapper::toDomain);
    }

    @Override
    @Transactional
    public Item save(Item item) {
        ItemEntity entity = ItemMapper.toEntity(item, entityManager);
        ItemEntity saved = itemJpaRepository.save(entity);
        return ItemMapper.toDomain(saved);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        itemJpaRepository.deleteById(id);
    }

    @Override
    public List<Item> findByAuctionId(Integer auctionId) {
        return itemJpaRepository.findByAuctionId(auctionId).stream()
                .map(ItemMapper::toDomain)
                .toList();
    }
}
