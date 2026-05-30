package com.ghtk.onlinebiddingproject.models.requests;

import lombok.Data;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Data
public class BidRequestDto {
    private Integer id;

    @NotNull(message = "Thiếu thông tin giá bid!")
    @Positive(message = "Giá không được nhỏ hơn hoặc bằng 0!")
    private Double price;
}
