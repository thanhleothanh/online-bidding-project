package com.ghtk.onlinebiddingproject.domain.model.auction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuctionQuery {

    private Integer ownerId;
    private AuctionStatus status;
    private String description;
    private Integer categoryId;
    private String sortBy;
    private String direction;

    public static Builder builder() {
        return new Builder();
    }

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
