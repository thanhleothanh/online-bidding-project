package com.ghtk.onlinebiddingproject.models.requests;

import lombok.Data;

@Data
public class UserChangeProfile {
    private String name;

    private String imageUrl;

    private String bio;
}
