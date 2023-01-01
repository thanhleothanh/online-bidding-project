package com.ghtk.onlinebiddingproject.services;

import com.ghtk.onlinebiddingproject.models.dtos.ReportResultDto;
import com.ghtk.onlinebiddingproject.models.entities.Report;
import com.ghtk.onlinebiddingproject.models.entities.ReportImage;
import com.ghtk.onlinebiddingproject.models.responses.ReportPagingResponse;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;

import java.util.List;

public interface ReportService {

    ReportPagingResponse get(Specification<Report> spec, HttpHeaders headers, Sort sort);

    Report save(Report report);

    Report adminGetById(Integer id);

    void adminDeleteById(Integer id);

    List<Report> adminGetReports();

    void adminJudgeReport(Integer id, ReportResultDto reportResultDto);

    ReportImage saveReportImage(Integer id, ReportImage reportImage);
}
