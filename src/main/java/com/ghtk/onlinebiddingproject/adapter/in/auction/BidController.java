package com.ghtk.onlinebiddingproject.adapter.in.auction;

import com.ghtk.onlinebiddingproject.adapter.in.auction.dto.BidRequestDto;
import com.ghtk.onlinebiddingproject.application.BidService;
import com.ghtk.onlinebiddingproject.domain.model.auction.Bid;
import com.ghtk.onlinebiddingproject.domain.model.shared.Money;
import com.ghtk.onlinebiddingproject.infrastructure.security.CurrentUserUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auctions/{auctionId}/bids")
public class BidController {

    private final BidService bidService;

    public BidController(BidService bidService) {
        this.bidService = bidService;
    }

    @GetMapping
    public ResponseEntity<List<Bid>> getBidsByAuctionId(@PathVariable Integer auctionId) {
        return ResponseEntity.ok(bidService.getByAuctionId(auctionId));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Bid> placeBid(
            @PathVariable Integer auctionId,
            @RequestBody BidRequestDto request) {
        Integer userId = CurrentUserUtils.getCurrentUserId();
        Money amount = Money.of(request.amount());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bidService.placeBid(auctionId, amount, userId));
    }
}
