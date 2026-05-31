package com.ghtk.onlinebiddingproject.domain.model.auction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuctionQuery {

    private Integer userId;
    private AuctionStatus status;
    private String description;
    private Integer categoryId;
    private String sortBy;
    private String direction;
}
