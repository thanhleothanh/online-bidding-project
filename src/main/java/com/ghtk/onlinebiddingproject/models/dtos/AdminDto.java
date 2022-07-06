package com.ghtk.onlinebiddingproject.models.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AdminDto {
    @NotNull(message = "thiếu thông tin tài khoản!")
    private Integer id;
}
