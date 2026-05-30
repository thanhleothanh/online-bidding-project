package com.ghtk.onlinebiddingproject.adapter.out.repository.database;

import com.ghtk.onlinebiddingproject.adapter.out.repository.database.mapper.AuctionMapper;
import com.ghtk.onlinebiddingproject.adapter.out.repository.database.persistence.CategoryEntity;
import com.ghtk.onlinebiddingproject.adapter.out.repository.database.springdata.CategoryJpaRepository;
import com.ghtk.onlinebiddingproject.domain.model.auction.Category;
import com.ghtk.onlinebiddingproject.domain.model.auction.CategoryRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

    private final CategoryJpaRepository categoryJpaRepository;

    public CategoryRepositoryImpl(CategoryJpaRepository categoryJpaRepository) {
        this.categoryJpaRepository = categoryJpaRepository;
    }

    @Override
    public List<Category> findAll() {
        return categoryJpaRepository.findAll().stream()
                .map(AuctionMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Category> findById(Integer id) {
        return categoryJpaRepository.findById(id)
                .map(AuctionMapper::toDomain);
    }
}
