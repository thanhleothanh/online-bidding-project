package com.ghtk.onlinebiddingproject.adapter.in.auth;

import com.ghtk.onlinebiddingproject.adapter.in.auth.dto.*;
import com.ghtk.onlinebiddingproject.application.AuthService;
import com.ghtk.onlinebiddingproject.infrastructure.security.CurrentUserUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserAuthResponse> signUp(@RequestBody UserSignup signupRequest,
                                                   HttpServletRequest request) {
        String applicationUrl = applicationUrl(request);
        var response = authService.signUp(signupRequest.username(), signupRequest.password(),
                signupRequest.name(), signupRequest.email(), signupRequest.roleId(), applicationUrl);
        return ResponseEntity.ok(toResponse(response));
    }

    @PostMapping("/login")
    public ResponseEntity<UserAuthResponse> login(@RequestBody UserLogin loginRequest,
                                                  HttpServletRequest request) {
        String applicationUrl = applicationUrl(request);
        var response = authService.login(loginRequest.username(), loginRequest.password(), applicationUrl);

        ResponseCookie jwtCookie = ResponseCookie.from("jwtToken", response.id().toString())
                .path("/").maxAge(30 * 24 * 60 * 60).httpOnly(true).secure(true).sameSite("None").build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(toResponse(response));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        ResponseCookie cleanCookie = ResponseCookie.from("jwtToken", null)
                .path("/").build();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cleanCookie.toString())
                .build();
    }

    @GetMapping("/verificationSignup")
    public ResponseEntity<String> verifyToken(@RequestParam String token) {
        String result = authService.validateToken(token);
        return switch (result) {
            case "valid" -> ResponseEntity.ok("Email verified successfully!");
            case "expired" -> ResponseEntity.badRequest().body("Verification token has expired!");
            default -> ResponseEntity.badRequest().body("Invalid verification token!");
        };
    }

    @PostMapping("/changePassword")
    public ResponseEntity<Void> changePassword(@RequestBody UserChangePassword changePassword) {
        Integer userId = CurrentUserUtils.getCurrentUserId();
        authService.changePassword(userId, changePassword.currentPassword(),
                changePassword.newPassword(), changePassword.confirmedNewPassword());
        return ResponseEntity.ok().build();
    }

    private UserAuthResponse toResponse(AuthService.AuthResponse response) {
        return new UserAuthResponse(response.id(), response.username(), response.name(),
                response.email(), response.imageUrl(), response.role(), response.status());
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + "/api/v1/auth/";
    }
}
