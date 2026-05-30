package com.ghtk.onlinebiddingproject.domain.model.auction;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    List<Category> findAll();
    Optional<Category> findById(Integer id);
}
