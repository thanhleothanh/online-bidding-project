package com.ghtk.onlinebiddingproject.utils;

import com.ghtk.onlinebiddingproject.constants.PagingHeadersConstants;
import com.ghtk.onlinebiddingproject.models.responses.AuctionPagingResponse;
import com.ghtk.onlinebiddingproject.models.responses.ProfilePagingResponse;
import com.ghtk.onlinebiddingproject.models.responses.ReportPagingResponse;
import org.springframework.http.HttpHeaders;

public class HttpHeadersUtils {
    public static HttpHeaders returnHttpHeaders(AuctionPagingResponse response) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(PagingHeadersConstants.COUNT.getProp(), String.valueOf(response.count()));
        headers.set(PagingHeadersConstants.PAGE.getProp(), String.valueOf(response.page()));
        headers.set(PagingHeadersConstants.PAGE_SIZE.getProp(), String.valueOf(response.pageSize()));
        headers.set(PagingHeadersConstants.PAGE_TOTAL.getProp(), String.valueOf(response.pageTotal()));
        return headers;
    }

    public static HttpHeaders returnHttpHeaders(ProfilePagingResponse response) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(PagingHeadersConstants.COUNT.getProp(), String.valueOf(response.count()));
        headers.set(PagingHeadersConstants.PAGE.getProp(), String.valueOf(response.page()));
        headers.set(PagingHeadersConstants.PAGE_SIZE.getProp(), String.valueOf(response.pageSize()));
        headers.set(PagingHeadersConstants.PAGE_TOTAL.getProp(), String.valueOf(response.pageTotal()));
        return headers;
    }

    public static HttpHeaders returnHttpHeaders(ReportPagingResponse response) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(PagingHeadersConstants.COUNT.getProp(), String.valueOf(response.count()));
        headers.set(PagingHeadersConstants.PAGE.getProp(), String.valueOf(response.page()));
        headers.set(PagingHeadersConstants.PAGE_SIZE.getProp(), String.valueOf(response.pageSize()));
        headers.set(PagingHeadersConstants.PAGE_TOTAL.getProp(), String.valueOf(response.pageTotal()));
        return headers;
    }
}
