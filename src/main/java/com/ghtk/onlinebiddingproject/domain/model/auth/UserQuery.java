package com.ghtk.onlinebiddingproject.domain.model.auth;

public class UserQuery {

    private UserStatus status;
    private String name;
    private String sortBy;
    private String direction;

    private UserQuery() {}

    public static Builder builder() {
        return new Builder();
    }

    public UserStatus getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public String getSortBy() {
        return sortBy;
    }

    public String getDirection() {
        return direction;
    }

    public static class Builder {
        private final UserQuery query;

        private Builder() {
            this.query = new UserQuery();
        }

        public Builder status(UserStatus status) {
            query.status = status;
            return this;
        }

        public Builder name(String name) {
            query.name = name;
            return this;
        }

        public Builder sortBy(String sortBy) {
            query.sortBy = sortBy;
            return this;
        }

        public Builder direction(String direction) {
            query.direction = direction;
            return this;
        }

        public UserQuery build() {
            return query;
        }
    }
}
