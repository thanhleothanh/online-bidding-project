package com.ghtk.onlinebiddingproject.constants;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ReportStatusConstants {
    PENDING(0),
    REJECTED(1),
    ACCEPTED(2);

    private final int status;
}
