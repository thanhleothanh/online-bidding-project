package com.ghtk.onlinebiddingproject.domain.model.review;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

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

    public boolean isAccepted() { return status != null && status.isAccepted(); }
    public boolean isRejected() { return status != null && status.isRejected(); }
}
