package com.ghtk.onlinebiddingproject.models.responses;

import com.ghtk.onlinebiddingproject.models.entities.Profile;

import java.util.List;

public record ProfilePagingResponse(Integer count, Integer page, Integer pageSize, Integer pageTotal, List<Profile> profiles) {
}
