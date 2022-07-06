package com.ghtk.onlinebiddingproject.models.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ReportDto {
    private Integer id;

    @NotBlank(message = "thiếu thông tin mô tả!")
    private String description;

    @NotNull(message = "thiếu thông tin người báo cáo!")
    private UserDto userReporter;

    @NotNull(message = "thiếu thông tin người bị báo cáo!")
    private UserDto userReported;

    private AuctionDto auction;
}
