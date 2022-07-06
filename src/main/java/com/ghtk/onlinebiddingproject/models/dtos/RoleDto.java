package com.ghtk.onlinebiddingproject.models.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RoleDto {
    private Integer id;

    @NotBlank(message = "tên vai trò không được để trống!")
    private String name;
}
