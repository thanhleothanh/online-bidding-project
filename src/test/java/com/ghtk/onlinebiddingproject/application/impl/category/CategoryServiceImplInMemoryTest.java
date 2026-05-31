package com.ghtk.onlinebiddingproject.application.impl.category;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ghtk.onlinebiddingproject.application.impl.test.inmemory.factories.TestRepositoryFactory;
import com.ghtk.onlinebiddingproject.domain.model.auction.Category;
import java.util.List;
import org.junit.jupiter.api.Test;

class CategoryServiceImplInMemoryTest extends AbstractCategoryServiceTest {

    @Override
    protected void initDependencies() {
        categoryRepository = TestRepositoryFactory.createCategoryRepository();
    }

    @Test
    void shouldReturnCategoriesWithExpectedNames() {
        List<Category> categories = categoryService.getAll();

        List<String> names = categories.stream().map(Category::getName).toList();
        assertTrue(names.contains("Electronics"));
        assertTrue(names.contains("Fashion"));
        assertTrue(names.contains("Home"));
    }
}
