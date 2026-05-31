package com.ghtk.onlinebiddingproject.application.impl.test.inmemory;

import com.ghtk.onlinebiddingproject.domain.model.auction.Category;
import com.ghtk.onlinebiddingproject.domain.model.auction.CategoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryCategoryRepository implements CategoryRepository {

    private final List<Category> categories = new ArrayList<>();

    public InMemoryCategoryRepository() {
        categories.add(new Category(1, "Electronics"));
        categories.add(new Category(2, "Fashion"));
        categories.add(new Category(3, "Home"));
    }

    @Override
    public List<Category> findAll() {
        return new ArrayList<>(categories);
    }

    @Override
    public Optional<Category> findById(Integer id) {
        return categories.stream().filter(c -> c.getId().equals(id)).findFirst();
    }
}
