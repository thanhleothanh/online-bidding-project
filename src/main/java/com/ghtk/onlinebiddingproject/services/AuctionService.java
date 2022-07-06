package com.ghtk.onlinebiddingproject.services;

import com.ghtk.onlinebiddingproject.models.dtos.AuctionDto;
import com.ghtk.onlinebiddingproject.models.entities.Auction;
import com.ghtk.onlinebiddingproject.models.responses.AuctionPagingResponse;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;

public interface AuctionService {
    AuctionPagingResponse get(Specification<Auction> spec, HttpHeaders headers, Sort sort);

    Auction getById(Integer id);

    Auction save(Auction auction);

    Auction put(AuctionDto auctionDto, Auction auction);

    void deleteById(Integer id);
}
