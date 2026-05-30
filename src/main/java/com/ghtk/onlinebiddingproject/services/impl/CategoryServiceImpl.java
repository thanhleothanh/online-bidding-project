package com.ghtk.onlinebiddingproject.services.impl;

import com.ghtk.onlinebiddingproject.models.entities.Category;
import com.ghtk.onlinebiddingproject.repositories.CategoryRepository;
import com.ghtk.onlinebiddingproject.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> get() {
        return categoryRepository.findAll();
    }
}
