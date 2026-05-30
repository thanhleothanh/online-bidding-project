package com.ghtk.onlinebiddingproject.domain.model.auction;

import com.ghtk.onlinebiddingproject.domain.model.shared.Money;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Bid {

    private Integer id;
    private Money price;
    private Integer userId;
    private Integer auctionId;
    private LocalDateTime bidTime;

    public static Bid create(Auction auction, Integer userId, Money amount) {
        BiddingRulesService.validateBid(auction, userId, amount);
        return new Bid(null, amount, userId, auction.getId(), LocalDateTime.now());
    }

}
