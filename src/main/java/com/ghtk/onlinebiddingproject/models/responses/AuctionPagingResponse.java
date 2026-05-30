package com.ghtk.onlinebiddingproject.models.responses;

import com.ghtk.onlinebiddingproject.models.entities.Auction;

import java.util.List;

public record AuctionPagingResponse(Integer count, Integer page, Integer pageSize, Integer pageTotal, List<Auction> auctions) {
}
