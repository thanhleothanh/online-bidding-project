package com.ghtk.onlinebiddingproject.models.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class BidDto {
    private Integer id;

    @NotBlank(message = "Giá không được để trống!")
    @Positive(message = "Giá không được nhỏ hơn hoặc bằng 0!")
    private Double price;

    @NotNull(message = "Thiếu thông tin user!")
    private UserDto user;

    @NotNull(message = "Thiếu thông tin bài đấu giá!")
    private AuctionDto auction;
}
