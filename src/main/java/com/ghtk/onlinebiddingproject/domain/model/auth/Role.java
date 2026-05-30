package com.ghtk.onlinebiddingproject.domain.model.auth;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public final class Role {

    private static final Role ADMIN = new Role(1, "ADMIN");
    private static final Role USER = new Role(2, "USER");

    private final Integer id;
    private final String name;

    private Role(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Role admin() {
        return ADMIN;
    }

    public static Role user() {
        return USER;
    }

    public static Role of(Integer id, String name) {
        return new Role(id, name);
    }

    public boolean isAdmin() {
        return "ADMIN".equals(name);
    }

    public boolean isUser() {
        return "USER".equals(name);
    }

}
