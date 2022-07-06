package com.ghtk.onlinebiddingproject.constants;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ReviewResultConstants {
    REJECTED(0),
    ACCEPTED(1);

    private final int result;
}
