package com.ghtk.onlinebiddingproject.models.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
public class ItemDto {

    private Integer id;

    @NotBlank(message = "tên sản phẩm không được để trống!")
    @Size(max = 200, message = "tên sản phẩm không được quá 200 ký tự!")
    private String name;

    @NotBlank(message = "mô tả sản phẩm không được để trống!")
    @Size(max = 1000, message = "mô tả sản phẩm không được quá 200 ký tự!")
    private String description;

    @NotNull(message = "số lượng sản phẩm không được trống!")
    @Positive(message = "số lượng sản phẩm không được nhỏ hơn hoặc bằng 0!")
    private Integer amount;
}
