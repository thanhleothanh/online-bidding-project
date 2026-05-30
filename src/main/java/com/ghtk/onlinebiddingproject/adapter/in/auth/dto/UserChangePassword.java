package com.ghtk.onlinebiddingproject.adapter.in.auth.dto;

public record UserChangePassword(String currentPassword, String newPassword, String confirmedNewPassword) {}
