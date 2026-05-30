package com.ghtk.onlinebiddingproject.domain.model.report;

public class ReportQuery {

    private Integer reporterId;
    private Integer reportedId;
    private Integer auctionId;
    private ReportResultStatus status;
    private String sortBy;
    private String direction;

    private ReportQuery() {}

    public static Builder builder() { return new Builder(); }

    public Integer getReporterId() { return reporterId; }
    public Integer getReportedId() { return reportedId; }
    public Integer getAuctionId() { return auctionId; }
    public ReportResultStatus getStatus() { return status; }
    public String getSortBy() { return sortBy; }
    public String getDirection() { return direction; }

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
