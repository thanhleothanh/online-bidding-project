package com.ghtk.onlinebiddingproject.constants;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserStatusConstants {

    BANNED(0),
    SUSPENDED(1),
    ACTIVE(2);

    private final int status;
}
