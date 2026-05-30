package com.ghtk.onlinebiddingproject.infrastructure.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class CurrentUserUtils {

    private CurrentUserUtils() {
    }

    public static UserDetailsImpl getCurrentUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("No authenticated user found");
        }
        return (UserDetailsImpl) authentication.getPrincipal();
    }

    public static Integer getCurrentUserId() {
        return getCurrentUserDetails().getId();
    }

    public static String getCurrentUsername() {
        return getCurrentUserDetails().getUsername();
    }

    public static void setCurrentUserDetails(Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public static boolean isAdmin() {
        UserDetailsImpl userDetails = getCurrentUserDetails();
        return userDetails.getAuthorities().stream()
                .findFirst()
                .map(a -> a.getAuthority().equals("ADMIN"))
                .orElse(false);
    }

    public static boolean isPostedByCurrentUser(Integer id) {
        UserDetailsImpl userDetails = getCurrentUserDetails();
        return userDetails.getId().equals(id);
    }
}
