package com.ghtk.onlinebiddingproject.domain.model.report;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ReviewResult {
    private Integer id;
    private ReviewResultStatus status;
    private Integer adminId;
    private Integer auctionId;

    public static ReviewResult judge(Integer adminId, ReviewResultStatus status) {
        return new ReviewResult(null, status, adminId, null);
    }
}
