package com.ghtk.onlinebiddingproject.adapter.in.auction.dto;

import java.time.LocalDateTime;

public record AuctionRequestDto(
        String description,
        LocalDateTime timeStart,
        LocalDateTime timeEnd,
        Double priceStart,
        Double priceStep,
        Integer categoryId,
        Integer itemId
) {}
