package com.ghtk.onlinebiddingproject.controllers;

import com.ghtk.onlinebiddingproject.exceptions.BadRequestException;
import com.ghtk.onlinebiddingproject.models.requests.UserLogin;
import com.ghtk.onlinebiddingproject.models.requests.UserSignup;
import com.ghtk.onlinebiddingproject.models.responses.CommonResponse;
import com.ghtk.onlinebiddingproject.models.responses.UserAuthResponse;
import com.ghtk.onlinebiddingproject.repositories.ProfileRepository;
import com.ghtk.onlinebiddingproject.services.impl.AuthServiceImpl;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private AuthServiceImpl authService;
    @Autowired
    private ProfileRepository profileRepository;

    @PostMapping("/login")
    public ResponseEntity<CommonResponse> loginUser(@Valid @RequestBody UserLogin loginRequest, HttpServletRequest request) {
        UserAuthResponse userLoginResponse = authService.login(loginRequest, request);
//        ResponseCookie jwtCookie = authService.generateJwtCookie();
        CommonResponse commonResponse = new CommonResponse(true, "Đăng nhập thành công!", userLoginResponse, null);
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @PostMapping("/signup")
    public ResponseEntity<CommonResponse> signUpUser(@Validated @RequestBody UserSignup signUpRequest, final HttpServletRequest request) {
        UserAuthResponse userRegisterResponse = authService.signUp(signUpRequest, request);
        CommonResponse commonResponse = new CommonResponse(true, "Đăng ký thành công!", userRegisterResponse, null);
        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<CommonResponse> logoutUser() {
//        ResponseCookie jwtCookie = authService.cleanJwtCookie();
        CommonResponse commonResponse = new CommonResponse(true, "Đăng xuất thành công!", null, null);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(commonResponse);
    }

    @GetMapping("/verificationSignup")
    public ResponseEntity<CommonResponse> verificationSignup(@RequestParam("token") String token) {
        String result = authService.validateVerificationToken(token);
        if (!result.equalsIgnoreCase("valid")) {
            throw new BadRequestException("Xác thực email thất bại!");
        }
        CommonResponse commonResponse = new CommonResponse(true, "Success", "Xác thực email thành công", null);
        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }
}
