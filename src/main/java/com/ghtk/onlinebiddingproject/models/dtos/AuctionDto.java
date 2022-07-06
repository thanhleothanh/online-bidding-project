package com.ghtk.onlinebiddingproject.models.dtos;


import com.ghtk.onlinebiddingproject.constants.AuctionStatusConstants;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Data
public class AuctionDto {
    private Integer id;

    //    @NotBlank(message = "Mô tả không được để trống!")
    @Size(max = 1000, message = "Mô tả không được quá 1000 ký tự!")
    private String description;

    //    @NotNull(message = "Thời gian bắt đầu không được trống!")
    private LocalDateTime timeStart;

    //    @NotNull(message = "Thời gian kết thúc không được trống!")
    private LocalDateTime timeEnd;

    @Min(value = 0, message = "Giá khởi điểm không được nhỏ hơn hoặc bằng 0!")
    private Double priceStart;

    @Min(value = 0, message = "Bước giá không được nhỏ hơn hoặc bằng 0!")
    private Double priceStep;

    @Min(value = 0, message = "Giá cao nhất không được nhỏ hơn hoặc bằng 0!")
    private Double highestPrice;

    private AuctionStatusConstants status;

    private UserDto user;

    private ItemDto item;

    private WinnerDto winner;
}
