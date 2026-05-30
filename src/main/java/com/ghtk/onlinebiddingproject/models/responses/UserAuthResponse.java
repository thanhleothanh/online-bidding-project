package com.ghtk.onlinebiddingproject.models.responses;

import com.ghtk.onlinebiddingproject.constants.UserStatusConstants;

public record UserAuthResponse(Integer id, String username, String name, String email, String imageUrl, String role, UserStatusConstants status) {
}
