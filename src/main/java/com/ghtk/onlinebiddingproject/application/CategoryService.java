package com.ghtk.onlinebiddingproject.application;

import com.ghtk.onlinebiddingproject.domain.model.auction.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAll();
}
