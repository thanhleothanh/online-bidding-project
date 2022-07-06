package com.ghtk.onlinebiddingproject.constants;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ReportResultConstants {

    REJECTED(0),
    ACCEPTED(1);

    private final int result;
}
