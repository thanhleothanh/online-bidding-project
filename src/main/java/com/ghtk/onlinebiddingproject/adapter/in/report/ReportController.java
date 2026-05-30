package com.ghtk.onlinebiddingproject.adapter.in.report;

import com.ghtk.onlinebiddingproject.adapter.in.report.dto.ReportRequestDto;
import com.ghtk.onlinebiddingproject.application.ReportService;
import com.ghtk.onlinebiddingproject.domain.model.report.Report;
import com.ghtk.onlinebiddingproject.infrastructure.security.CurrentUserUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Report> submit(@RequestBody ReportRequestDto request) {
        Integer userId = CurrentUserUtils.getCurrentUserId();
        Report report = Report.create(userId, request.reportedId(), request.auctionId(), request.description());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reportService.submit(report, userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Report> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(reportService.getById(id));
    }
}
