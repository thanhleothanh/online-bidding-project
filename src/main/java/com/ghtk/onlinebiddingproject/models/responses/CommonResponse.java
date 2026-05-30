package com.ghtk.onlinebiddingproject.models.responses;

public record CommonResponse(boolean success, String message, Object data, Object errors) {
}
