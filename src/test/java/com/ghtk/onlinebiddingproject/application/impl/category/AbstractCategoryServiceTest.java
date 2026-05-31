package com.ghtk.onlinebiddingproject.application.impl.category;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.ghtk.onlinebiddingproject.application.impl.CategoryServiceImpl;
import com.ghtk.onlinebiddingproject.domain.model.auction.CategoryRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public abstract class AbstractCategoryServiceTest {

    protected CategoryRepository categoryRepository;
    protected CategoryServiceImpl categoryService;

    @BeforeEach
    void setUp() {
        initDependencies();
        categoryService = new CategoryServiceImpl(categoryRepository);
    }

    protected abstract void initDependencies();

    @Test
    void shouldGetAllCategories() {
        List<?> categories = categoryService.getAll();

        assertNotNull(categories);
        assertFalse(categories.isEmpty());
    }
}
