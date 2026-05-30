package com.ghtk.onlinebiddingproject.domain.model.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserQuery {

    private UserStatus status;
    private String name;
    private String sortBy;
    private String direction;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final UserQuery query = new UserQuery();

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
