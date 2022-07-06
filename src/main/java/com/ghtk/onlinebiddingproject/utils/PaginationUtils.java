package com.ghtk.onlinebiddingproject.utils;

import com.ghtk.onlinebiddingproject.constants.PagingHeadersConstants;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;

import java.util.Objects;

public class PaginationUtils {
    public static boolean isPaginationRequested(HttpHeaders headers) {
        return headers.containsKey(PagingHeadersConstants.PAGE.getProp()) && headers.containsKey(PagingHeadersConstants.PAGE_SIZE.getProp());
    }

    public static Pageable buildPageRequest(HttpHeaders headers, Sort sort) {
        int page = Integer.parseInt(Objects.requireNonNull(headers.get(PagingHeadersConstants.PAGE.getProp())).get(0));
        int size = Integer.parseInt(Objects.requireNonNull(headers.get(PagingHeadersConstants.PAGE_SIZE.getProp())).get(0));
        return PageRequest.of(page, size, sort);
    }
}
