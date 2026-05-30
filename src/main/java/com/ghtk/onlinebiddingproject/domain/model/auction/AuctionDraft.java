package com.ghtk.onlinebiddingproject.domain.model.auction;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class AuctionDraft {

    private final String description;
    private final LocalDateTime timeStart;
    private final LocalDateTime timeEnd;
    private final double priceStart;
    private final double priceStep;
    private final Integer categoryId;
    private final Integer itemId;

}
