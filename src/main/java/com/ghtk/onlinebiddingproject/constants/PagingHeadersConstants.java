package com.ghtk.onlinebiddingproject.constants;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PagingHeadersConstants {
    PAGE_SIZE("page_size"),
    PAGE("page"),
    PAGE_TOTAL("page_total"),
    COUNT("count");

    private final String prop;
}
