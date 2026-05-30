package com.ghtk.onlinebiddingproject.models.requests;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Data
public class UserLogin {

    @NotBlank(message = "Username không được để trống!")
    @Pattern(regexp = "[a-zA-Z0-9_.]*")
    private String username;

    @NotBlank(message = "Password không được để trống!")
    private String password;
}
