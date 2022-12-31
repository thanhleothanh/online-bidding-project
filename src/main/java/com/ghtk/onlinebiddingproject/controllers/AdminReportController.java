package com.ghtk.onlinebiddingproject.controllers;

import com.ghtk.onlinebiddingproject.models.dtos.ReportResultDto;
import com.ghtk.onlinebiddingproject.models.entities.Notification;
import com.ghtk.onlinebiddingproject.models.entities.Report;
import com.ghtk.onlinebiddingproject.models.responses.CommonResponse;
import com.ghtk.onlinebiddingproject.models.responses.ReportPagingResponse;
import com.ghtk.onlinebiddingproject.models.responses.ReportPagingResponseDto;
import com.ghtk.onlinebiddingproject.services.impl.NotificationServiceImpl;
import com.ghtk.onlinebiddingproject.services.impl.ReportServiceImpl;
import com.ghtk.onlinebiddingproject.utils.HttpHeadersUtils;
import com.ghtk.onlinebiddingproject.utils.converters.EntityToDtoConverter;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "api/v1/admin/reports")
public class AdminReportController {

    @Autowired
    private ReportServiceImpl reportService;
    @Autowired
    private NotificationServiceImpl notificationService;
    @Autowired
    private EntityToDtoConverter entityToDtoConverter;

    @GetMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<CommonResponse> adminGetReports(
            @And({
                    @Spec(path = "result", params = "result", spec = Equal.class),
            })
            Specification<Report> spec,
            Sort sort,
            @RequestHeader HttpHeaders headers) {
        ReportPagingResponse pagingResponse = reportService.get(spec, headers, Sort.by(Sort.Direction.DESC, "createdAt"));
        ReportPagingResponseDto dtoResponse = entityToDtoConverter.convertToDto(pagingResponse);
        CommonResponse response = new CommonResponse(true, "Success", dtoResponse, null);
        return new ResponseEntity<>(response, HttpHeadersUtils.returnHttpHeaders(pagingResponse), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<CommonResponse> adminDeleteById(@PathVariable("id") int id) {
        reportService.adminDeleteById(id);
        CommonResponse response = new CommonResponse(true, "Deleted report!", null, null);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{id}/results")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<CommonResponse> adminJudgeReport(@PathVariable("id") int id, @Valid @RequestBody ReportResultDto reportResultDto) {
        reportService.adminJudgeReport(id, reportResultDto);
        CommonResponse response = new CommonResponse(true, "Success", null, null);

        //send notification
        Report report = reportService.adminGetById(id);
        Notification notification = notificationService.createJudgeReportNotification(report);
        notificationService.notifyThroughSocket(notification);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
