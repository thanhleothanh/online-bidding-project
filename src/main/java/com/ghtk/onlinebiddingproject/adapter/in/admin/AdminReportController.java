package com.ghtk.onlinebiddingproject.adapter.in.admin;

import com.ghtk.onlinebiddingproject.adapter.in.admin.dto.AdminReportJudgeRequestDto;
import com.ghtk.onlinebiddingproject.application.ReportService;
import com.ghtk.onlinebiddingproject.domain.model.report.Report;
import com.ghtk.onlinebiddingproject.domain.model.report.ReportQuery;
import com.ghtk.onlinebiddingproject.infrastructure.security.CurrentUserUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/reports")
public class AdminReportController {

    private final ReportService reportService;

    public AdminReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
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

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Report> getReport(@PathVariable Integer id) {
        return ResponseEntity.ok(reportService.adminGetById(id));
    }

    @PostMapping("/{id}/results")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Report> judgeReport(
            @PathVariable Integer id,
            @RequestBody AdminReportJudgeRequestDto dto) {
        Integer adminId = CurrentUserUtils.getCurrentUserId();
        return ResponseEntity.ok(reportService.adminJudge(id, dto.status(), adminId));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteReport(@PathVariable Integer id) {
        reportService.adminDelete(id);
        return ResponseEntity.noContent().build();
    }
}
