package com.ghtk.onlinebiddingproject.domain.model.report;

import java.util.Optional;

public interface ReviewResultRepository {
    ReviewResult save(ReviewResult result);
    Optional<ReviewResult> findByAuctionId(Integer auctionId);
}
