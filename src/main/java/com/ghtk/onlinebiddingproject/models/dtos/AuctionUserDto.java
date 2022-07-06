package com.ghtk.onlinebiddingproject.models.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AuctionUserDto {
    private Integer id;

    @NotNull(message = "thiếu thông tin bài đấu giá!")
    private AuctionDto auction;

    @NotNull(message = "thiếu thông tin user")
    private UserDto user;
}
