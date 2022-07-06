package com.ghtk.onlinebiddingproject.models.dtos;

import com.ghtk.onlinebiddingproject.constants.UserStatusConstants;
import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;

import javax.validation.constraints.NotBlank;

@Data
public class ProfileDto {
    private Integer id;

    @NotBlank(message = "username không được để trống!")
    private String username;

    @NotBlank(message = "password không được để trống!")
    @JsonIgnore
    private String password;

    @NotBlank(message = "name không được để trống!")
    private String name;

    private String email;

    private String imageUrl;

    private UserStatusConstants status;

    private RoleDto role;

}
