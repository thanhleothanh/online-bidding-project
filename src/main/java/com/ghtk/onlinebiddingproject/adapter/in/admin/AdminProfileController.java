package com.ghtk.onlinebiddingproject.adapter.in.admin;

import com.ghtk.onlinebiddingproject.adapter.in.admin.dto.AdminProfileStatusRequestDto;
import com.ghtk.onlinebiddingproject.application.AuctionService;
import com.ghtk.onlinebiddingproject.application.ReportService;
import com.ghtk.onlinebiddingproject.domain.model.auction.Auction;
import com.ghtk.onlinebiddingproject.domain.model.auction.AuctionQuery;
import com.ghtk.onlinebiddingproject.domain.model.auction.AuctionStatus;
import com.ghtk.onlinebiddingproject.domain.model.report.Report;
import com.ghtk.onlinebiddingproject.domain.model.report.ReportQuery;
import com.ghtk.onlinebiddingproject.infrastructure.security.CurrentUserUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/profiles")
public class AdminProfileController {

    private final AuctionService auctionService;

    public AdminProfileController(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @GetMapping("/{userId}/auctions")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Auction>> getUserAuctions(@PathVariable Integer userId) {
        return ResponseEntity.ok(auctionService.getAuctionsByUserId(userId));
    }
}
