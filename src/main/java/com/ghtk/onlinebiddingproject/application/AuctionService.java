package com.ghtk.onlinebiddingproject.application;

import com.ghtk.onlinebiddingproject.domain.model.auction.Auction;
import com.ghtk.onlinebiddingproject.domain.model.auction.AuctionQuery;
import com.ghtk.onlinebiddingproject.domain.model.auction.AuctionStatus;

import java.util.List;

public interface AuctionService {
    Auction getById(Integer id, Integer userId);
    List<Auction> get(AuctionQuery query, int page, int size);
    List<Auction> getTopTrending();
    List<Auction> getMyAuctions(Integer userId, AuctionStatus status);
    List<Auction> getWonAuctionsByUserId(Integer userId);
    List<Auction> getAuctionsByUserId(Integer userId);
    Auction create(Auction auction, Integer userId);
    Auction update(Integer id, Auction auction, Integer userId);
    Auction submitPending(Integer id, Integer userId);
    void delete(Integer id, Integer userId);
    Auction adminGetById(Integer id);
    Auction adminPut(Auction auction, Integer id);
    Auction adminReview(Auction auction, Integer id, Integer adminId);
    void adminDelete(Integer id);
}
