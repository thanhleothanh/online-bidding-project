package com.ghtk.onlinebiddingproject.domain.model.auction;

import com.ghtk.onlinebiddingproject.domain.model.shared.Money;

import java.time.LocalDateTime;
import java.util.Objects;

public class Bid {

    private Integer id;
    private Money price;
    private Integer userId;
    private Integer auctionId;
    private LocalDateTime bidTime;

    public Bid(Integer id, Money price, Integer userId, Integer auctionId, LocalDateTime bidTime) {
        this.id = id;
        this.price = price;
        this.userId = userId;
        this.auctionId = auctionId;
        this.bidTime = bidTime;
    }

    public static Bid create(Auction auction, Integer userId, Money amount) {
        BiddingRulesService.validateBid(auction, userId, amount);
        return new Bid(null, amount, userId, auction.getId(), LocalDateTime.now());
    }

    public Integer getId() { return id; }
    public Money getPrice() { return price; }
    public Integer getUserId() { return userId; }
    public Integer getAuctionId() { return auctionId; }
    public LocalDateTime getBidTime() { return bidTime; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bid bid = (Bid) o;
        return Objects.equals(id, bid.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
