package com.ghtk.onlinebiddingproject.models.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class WinnerDto {
    private Integer id;

    @NotNull(message = "thiếu thông tin lệnh đấu giá!")
    private BidDto bid;
}
