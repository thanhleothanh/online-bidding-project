package com.ghtk.onlinebiddingproject.adapter.in.auction;

import com.ghtk.onlinebiddingproject.adapter.in.auction.dto.AuctionRequestDto;
import com.ghtk.onlinebiddingproject.application.AuctionService;
import com.ghtk.onlinebiddingproject.domain.model.auction.Auction;
import com.ghtk.onlinebiddingproject.domain.model.auction.AuctionDraft;
import com.ghtk.onlinebiddingproject.domain.model.auction.AuctionStatus;
import com.ghtk.onlinebiddingproject.infrastructure.security.CurrentUserUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auctions")
public class AuctionController {

    private final AuctionService auctionService;

    public AuctionController(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @GetMapping
    public ResponseEntity<List<Auction>> get(
            @RequestParam(required = false) AuctionStatus status,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        var query = com.ghtk.onlinebiddingproject.domain.model.auction.AuctionQuery.builder()
                .status(status)
                .description(description)
                .categoryId(categoryId)
                .build();
        return ResponseEntity.ok(auctionService.get(query, page, size));
    }

    @GetMapping("/topTrending")
    public ResponseEntity<List<Auction>> getTopTrending() {
        return ResponseEntity.ok(auctionService.getTopTrending());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Auction> getById(@PathVariable Integer id) {
        Integer userId = CurrentUserUtils.getCurrentUserId();
        return ResponseEntity.ok(auctionService.getById(id, userId));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Auction> create(@RequestBody AuctionRequestDto request) {
        Integer userId = CurrentUserUtils.getCurrentUserId();
        AuctionDraft draft = new AuctionDraft(
                request.description(),
                request.timeStart(),
                request.timeEnd(),
                request.priceStart(),
                request.priceStep(),
                request.categoryId(),
                request.itemId()
        );
        Auction auction = Auction.create(draft, userId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(auctionService.create(auction, userId));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Auction> update(@PathVariable Integer id, @RequestBody AuctionRequestDto request) {
        Integer userId = CurrentUserUtils.getCurrentUserId();
        AuctionDraft draft = new AuctionDraft(
                request.description(),
                request.timeStart(),
                request.timeEnd(),
                request.priceStart(),
                request.priceStep(),
                request.categoryId(),
                request.itemId()
        );
        Auction auction = Auction.create(draft, userId);
        return ResponseEntity.ok(auctionService.update(id, auction, userId));
    }

    @PutMapping("/{id}/submit")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Auction> submitPending(@PathVariable Integer id) {
        Integer userId = CurrentUserUtils.getCurrentUserId();
        return ResponseEntity.ok(auctionService.submitPending(id, userId));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        Integer userId = CurrentUserUtils.getCurrentUserId();
        auctionService.delete(id, userId);
        return ResponseEntity.noContent().build();
    }
}
