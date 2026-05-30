package com.ghtk.onlinebiddingproject.adapter.in.notification;

import com.ghtk.onlinebiddingproject.application.NotificationService;
import com.ghtk.onlinebiddingproject.domain.model.notification.Notification;
import com.ghtk.onlinebiddingproject.infrastructure.security.CurrentUserUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<List<Notification>> getMyNotifications(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Integer userId = CurrentUserUtils.getCurrentUserId();
        return ResponseEntity.ok(notificationService.getMyNotifications(userId, page, size));
    }
}
