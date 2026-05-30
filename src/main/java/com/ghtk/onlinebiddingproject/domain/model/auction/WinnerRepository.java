package com.ghtk.onlinebiddingproject.domain.model.auction;

import java.util.List;
import java.util.Optional;

public interface WinnerRepository {
    Winner save(Winner winner);
    Optional<Winner> findByAuctionId(Integer auctionId);
    List<Winner> findByUserId(Integer userId);
}
