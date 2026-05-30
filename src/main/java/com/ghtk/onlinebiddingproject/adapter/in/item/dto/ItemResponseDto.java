package com.ghtk.onlinebiddingproject.adapter.in.item.dto;

import java.util.List;

public record ItemResponseDto(
        Integer id,
        String name,
        String description,
        Integer amount,
        List<ItemImageResponseDto> images
) {
}
