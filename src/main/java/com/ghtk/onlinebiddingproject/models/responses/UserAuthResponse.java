package com.ghtk.onlinebiddingproject.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthResponse {
    private Integer id;
    private String username;
    private String name;
    private List<Object> roles;
}
