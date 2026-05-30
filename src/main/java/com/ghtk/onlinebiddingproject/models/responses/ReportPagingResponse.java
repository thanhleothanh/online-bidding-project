package com.ghtk.onlinebiddingproject.models.responses;

import com.ghtk.onlinebiddingproject.models.entities.Report;

import java.util.List;

public record ReportPagingResponse(Integer count, Integer page, Integer pageSize, Integer pageTotal, List<Report> reports) {
}
