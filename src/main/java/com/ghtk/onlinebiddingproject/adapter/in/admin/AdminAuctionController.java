package com.ghtk.onlinebiddingproject.adapter.in.admin;

import com.ghtk.onlinebiddingproject.adapter.in.admin.dto.AdminAuctionApproveRequestDto;
import com.ghtk.onlinebiddingproject.adapter.in.admin.dto.AdminReportJudgeRequestDto;
import com.ghtk.onlinebiddingproject.application.AuctionService;
import com.ghtk.onlinebiddingproject.application.ReportService;
import com.ghtk.onlinebiddingproject.domain.model.auction.Auction;
import com.ghtk.onlinebiddingproject.domain.model.auction.AuctionQuery;
import com.ghtk.onlinebiddingproject.domain.model.auction.AuctionStatus;
import com.ghtk.onlinebiddingproject.domain.model.report.Report;
import com.ghtk.onlinebiddingproject.domain.model.report.ReportQuery;
import com.ghtk.onlinebiddingproject.infrastructure.security.CurrentUserUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminAuctionController {

    private final AuctionService auctionService;
    private final ReportService reportService;

    public AdminAuctionController(AuctionService auctionService, ReportService reportService) {
        this.auctionService = auctionService;
        this.reportService = reportService;
    }

    @GetMapping("/auctions")
    public ResponseEntity<List<Auction>> listAuctions(
            @RequestParam(required = false) AuctionStatus status,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        AuctionQuery query = AuctionQuery.builder()
                .status(status)
                .description(description)
                .categoryId(categoryId)
                .build();
        return ResponseEntity.ok(auctionService.get(query, page, size));
    }

    @GetMapping("/auctions/{id}")
    public ResponseEntity<Auction> getAuction(@PathVariable Integer id) {
        return ResponseEntity.ok(auctionService.adminGetById(id));
    }

    @PutMapping("/auctions/{id}")
    public ResponseEntity<Auction> updateAuction(@PathVariable Integer id, @RequestBody Auction auction) {
        return ResponseEntity.ok(auctionService.adminPut(auction, id));
    }

    @PutMapping("/auctions/{id}/approve")
    public ResponseEntity<Auction> approveAuction(
            @PathVariable Integer id,
            @RequestBody AdminAuctionApproveRequestDto dto) {
        Integer adminId = CurrentUserUtils.getCurrentUserId();
        Auction auction = new Auction(null, null, null, null, null, null, dto.status(), null, null, null);
        return ResponseEntity.ok(auctionService.adminReview(auction, id, adminId));
    }

    @DeleteMapping("/auctions/{id}")
    public ResponseEntity<Void> deleteAuction(@PathVariable Integer id) {
        auctionService.adminDelete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/reports")
    public ResponseEntity<List<Report>> listReports(
            @RequestParam(required = false) Integer reporterId,
            @RequestParam(required = false) Integer reportedId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        ReportQuery query = ReportQuery.builder()
                .reporterId(reporterId)
                .reportedId(reportedId)
                .build();
        return ResponseEntity.ok(reportService.getAll(query, page, size));
    }

    @GetMapping("/reports/{id}")
    public ResponseEntity<Report> getReport(@PathVariable Integer id) {
        return ResponseEntity.ok(reportService.adminGetById(id));
    }

    @PostMapping("/reports/{id}/results")
    public ResponseEntity<Report> judgeReport(
            @PathVariable Integer id,
            @RequestBody AdminReportJudgeRequestDto dto) {
        Integer adminId = CurrentUserUtils.getCurrentUserId();
        return ResponseEntity.ok(reportService.adminJudge(id, dto.status(), adminId));
    }

    @DeleteMapping("/reports/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable Integer id) {
        reportService.adminDelete(id);
        return ResponseEntity.ok().build();
    }
}
