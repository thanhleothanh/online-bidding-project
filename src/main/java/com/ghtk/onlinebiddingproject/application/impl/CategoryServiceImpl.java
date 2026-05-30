package com.ghtk.onlinebiddingproject.application.impl;

import com.ghtk.onlinebiddingproject.application.CategoryService;
import com.ghtk.onlinebiddingproject.domain.model.auction.Category;
import com.ghtk.onlinebiddingproject.domain.model.auction.CategoryRepository;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }
}
