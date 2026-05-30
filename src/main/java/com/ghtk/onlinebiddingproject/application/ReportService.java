package com.ghtk.onlinebiddingproject.application;

import com.ghtk.onlinebiddingproject.domain.model.report.*;

import java.util.List;

public interface ReportService {
    Report submit(Report report, Integer userId);
    Report getById(Integer id);
    List<Report> getAll(ReportQuery query, int page, int size);
    Report adminGetById(Integer id);
    void adminDelete(Integer id);
    Report adminJudge(Integer id, ReportResultStatus status, Integer adminId);
}
