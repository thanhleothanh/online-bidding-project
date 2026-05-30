package com.ghtk.onlinebiddingproject.domain.model.report;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportQuery {

    private Integer reporterId;
    private Integer reportedId;
    private Integer auctionId;
    private ReportResultStatus status;
    private String sortBy;
    private String direction;

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private final ReportQuery query = new ReportQuery();
        public Builder reporterId(Integer id) { query.reporterId = id; return this; }
        public Builder reportedId(Integer id) { query.reportedId = id; return this; }
        public Builder auctionId(Integer id) { query.auctionId = id; return this; }
        public Builder status(ReportResultStatus s) { query.status = s; return this; }
        public Builder sortBy(String s) { query.sortBy = s; return this; }
        public Builder direction(String d) { query.direction = d; return this; }
        public ReportQuery build() { return query; }
    }
}
