package com.ghtk.onlinebiddingproject.domain.model.report;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ReportResult {

    private Integer id;
    private ReportResultStatus status;
    private Integer adminId;
    private Integer reportId;

    public static ReportResult judge(Integer adminId, ReportResultStatus status) {
        return new ReportResult(null, status, adminId, null);
    }

    public boolean isAccepted() { return status != null && status.isAccepted(); }
    public boolean isRejected() { return status != null && status.isRejected(); }

}
