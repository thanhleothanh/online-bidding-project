package com.ghtk.onlinebiddingproject.application.impl.category;

import com.ghtk.onlinebiddingproject.domain.model.auction.CategoryRepository;
import com.ghtk.onlinebiddingproject.infrastructure.IntegrationDatabaseTest;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationDatabaseTest
class CategoryServiceImplDatabaseTest extends AbstractCategoryServiceTest {

    @Autowired
    private CategoryRepository autCategoryRepository;

    @Override
    protected void initDependencies() {
        categoryRepository = autCategoryRepository;
    }
}
