package com.ghtk.onlinebiddingproject.domain.model.auction;

import java.time.LocalDateTime;

public class AuctionDraft {

    private final String description;
    private final LocalDateTime timeStart;
    private final LocalDateTime timeEnd;
    private final double priceStart;
    private final double priceStep;
    private final Integer categoryId;
    private final Integer itemId;

    public AuctionDraft(String description, LocalDateTime timeStart, LocalDateTime timeEnd,
                       double priceStart, double priceStep, Integer categoryId, Integer itemId) {
        this.description = description;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.priceStart = priceStart;
        this.priceStep = priceStep;
        this.categoryId = categoryId;
        this.itemId = itemId;
    }

    public String getDescription() { return description; }
    public LocalDateTime getTimeStart() { return timeStart; }
    public LocalDateTime getTimeEnd() { return timeEnd; }
    public double getPriceStart() { return priceStart; }
    public double getPriceStep() { return priceStep; }
    public Integer getCategoryId() { return categoryId; }
    public Integer getItemId() { return itemId; }
}
