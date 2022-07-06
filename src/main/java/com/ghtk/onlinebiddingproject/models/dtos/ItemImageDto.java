package com.ghtk.onlinebiddingproject.models.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ItemImageDto {
    private Integer id;

    @NotBlank(message = "ảnh sản phẩm không được để trống!")
    private String imageUrl;

    @NotNull(message = "sản phẩm không được để trống!")
    private ItemDto item;
}
