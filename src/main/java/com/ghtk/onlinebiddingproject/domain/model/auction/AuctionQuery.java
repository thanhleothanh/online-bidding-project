package com.ghtk.onlinebiddingproject.domain.model.auction;

public class AuctionQuery {

    private Integer ownerId;
    private AuctionStatus status;
    private String description;
    private Integer categoryId;
    private String sortBy;
    private String direction;

    private AuctionQuery() {
    }

    public static Builder builder() {
        return new Builder();
    }

    public Integer getOwnerId() { return ownerId; }
    public AuctionStatus getStatus() { return status; }
    public String getDescription() { return description; }
    public Integer getCategoryId() { return categoryId; }
    public String getSortBy() { return sortBy; }
    public String getDirection() { return direction; }

    public static class Builder {
        private final AuctionQuery query = new AuctionQuery();

        public Builder ownerId(Integer ownerId) { query.ownerId = ownerId; return this; }
        public Builder status(AuctionStatus status) { query.status = status; return this; }
        public Builder description(String description) { query.description = description; return this; }
        public Builder categoryId(Integer categoryId) { query.categoryId = categoryId; return this; }
        public Builder sortBy(String sortBy) { query.sortBy = sortBy; return this; }
        public Builder direction(String direction) { query.direction = direction; return this; }
        public AuctionQuery build() { return query; }
    }
}
